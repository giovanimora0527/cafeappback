/*
* Archivo: CieServiceImpl.java
* Fecha: 20/02/2019
* Todos los derechos de propiedad intelectual e industrial sobre esta
* aplicacion son de propiedad exclusiva del GRUPO ASESORIA EN
* SISTEMATIZACION DE DATOS SOCIEDAD POR ACCIONES SIMPLIFICADA GRUPO ASD
  S.A.S.
* Su uso, alteracion, reproduccion o modificacion sin la debida
* consentimiento por escrito de GRUPO ASD S.A.S.
* autorizacion por parte de su autor quedan totalmente prohibidos.
*
* Este programa se encuentra protegido por las disposiciones de la
* Ley 23 de 1982 y demas normas concordantes sobre derechos de autor y
* propiedad intelectual. Su uso no autorizado dara lugar a las sanciones
* previstas en la Ley.
*/
package co.com.grupoasd.fae.administracion.service;

import co.com.grupoasd.fae.exception.BadRequestException;
import co.com.grupoasd.fae.exception.ErrorRs;
import co.com.grupoasd.fae.exception.NotFoundException;
import co.com.grupoasd.fae.model.dto.CieDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cie;
import co.com.grupoasd.fae.model.repository.CieRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementacion de CieService
 *
 * @author GMORA lmora@grupoasd.com.co
 */
@Service
public class CieServiceImpl implements CieService {

    private final CieRepository cieRepository;

    @Value("${cie.upload-dir}")
    private String rutaFilesCie;
    @Value("${alice.upload-dir}")
    private String rutaFilesAlice;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public CieServiceImpl(CieRepository cieRepository) {
        this.cieRepository = cieRepository;
    }

    @Transactional
    @Override
    public Cie crear(Cie cie, Long userId) {
        Cie cieExiste = this.cieRepository.findByCodigoAndNorma(cie.getCodigo(), cie.getNorma());
        if (cieExiste != null) {
            cie.setId(cieExiste.getId());
            return editar(cie, userId);
        } else {
            List<Cie> cieList = cieRepository.findByDetalleAndNorma(cie.getDescripcion(), cie.getNorma());
            if (cieList != null && !cieList.isEmpty()) {
                throw new BadRequestException(ErrorRs.get("No se puede crear el CUPS", 
                        "La descripción ya está asociada al CIE " + cieList.get(0).getCodigo(), 400));
            }
            cie.setCreadoPor(userId);
            cie.setFechaCreacion(Date.from(Instant.now()));
        }

        return cieRepository.save(cie);
    }

    @Override
    public Cie editar(Cie cie, Long userId) {
        if (cie.getId() != null) {
            Optional<Cie> cieExiste = cieRepository.findById(cie.getId());
            if (cieExiste.isPresent()) {
                cie.setCreadoPor(cieExiste.get().getCreadoPor());
                cie.setFechaCreacion(cieExiste.get().getFechaCreacion());
                cie.setModificadoPor(userId);
                cie.setFechaModificacion(Date.from(Instant.now()));
                return cieRepository.save(cie);
            }
        }
        throw new NotFoundException(ErrorRs.get("No existe el CIE a modificar", null, 404));
    }

    private void setCieDtoError(String error, String[] line, List<CieDtoError> listCieError) {
        CieDtoError cieDtoError = new CieDtoError();
        cieDtoError.setLinea(Arrays.toString(line));
        cieDtoError.setDetalleError(error);
        listCieError.add(cieDtoError);
    }

    /**
     * Metodo que genera el archivo de error cuando un cie no carga
     *
     * @param listCieError
     * @param nombreArchivo
     * @throws IOException
     */
    private void guardarArchivoErrores(List<CieDtoError> listCieError, String nombreArchivo) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("linea", CsvSchema.ColumnType.STRING)
                .addColumn("detalleError", CsvSchema.ColumnType.STRING)
                .build().withHeader();
        schema = schema.withColumnSeparator(';');
        ObjectWriter myObjectWriter = mapper.writer(schema);
        File tempFile = new File(rutaFilesAlice + rutaFilesCie, nombreArchivo.concat(".csv"));
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
        myObjectWriter.writeValue(writerOutputStream, listCieError);
    }

    @Override
    public List<String> getArchivosNoCargados() {
        List<String> rutas = new LinkedList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(rutaFilesAlice + rutaFilesCie))) {
            for (Path path : directoryStream) {
                rutas.add(path.getFileName().toString());
            }
        } catch (IOException | DirectoryIteratorException ex) {
            throw new RuntimeException("Ocurrio un error interno");
        }
        return rutas;
    }

    @Override
    public GenericRs cargarArchivo(MultipartFile archivo, Long idUser, String norma) {
        boolean existeData = false;
        try {
            List<CieDtoError> listCieError = new LinkedList<>();
            CSVReader csvReader;
            try (Reader reader = new InputStreamReader(archivo.getInputStream())) {
                CSVParser parser = new CSVParserBuilder()
                        .withSeparator(';')
                        .withIgnoreQuotations(true)
                        .build();
                csvReader = new CSVReaderBuilder(reader)
                        .withSkipLines(1)
                        .withCSVParser(parser)
                        .build();
                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    existeData = true;
                    setCie(line, idUser, listCieError, norma);
                }
            }
            csvReader.close();
            if (!listCieError.isEmpty()) {
                guardarArchivoErrores(listCieError, archivo.getOriginalFilename().substring(0, archivo.getOriginalFilename().lastIndexOf(".")).concat("_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
                return new GenericRs("201", "Cargue completado, verifique inconsistencias");
            }
        } catch (IOException e) {
            throw new RuntimeException("Ocurrio un error cargando el archivo");
        } catch (Exception e) {
            throw new RuntimeException("Hay un error en la estructura del archivo, verifique");
        }
        if (!existeData) {
            throw new BadRequestException(ErrorRs.get("No se puede cargar el archivo",
                                "No existen datos para el cargue.", 400));
        }
        return new GenericRs("200", "Cargue completado");
    }

    private void setCie(String[] line, Long idUser, List<CieDtoError> listCieError, String norma) {
        try {
            if (line.length < 2) {
                setCieDtoError("Campos vacios o incompletos", line, listCieError);
                return;
            }
            if (StringUtils.isEmpty(line[0])) {
                setCieDtoError("Campo codigo no puede ser vacio", line, listCieError);
                return;
            }
            if (StringUtils.isEmpty(line[1])) {
                setCieDtoError("Campo detalle no puede ser vacio", line, listCieError);
                return;
            }
            if (line[0].trim().length() > 6) {
                setCieDtoError("Campo codigo no cumple con la longitud maxima de 6", line, listCieError);
                return;
            } else if (line[1].trim().length() > 255) {
                setCieDtoError("Campo detalle no cumple con la longitud maxima de 255", line, listCieError);
                return;
            } else if (line[1].trim().equals("")) {
                setCieDtoError("Campo detalle no puede estar vacío", line, listCieError);
                return;
            }

            Cie cie = new Cie();
            cie.setCodigo(line[0].trim());
            cie.setDetalle(line[1].trim());
            cie.setNorma(Long.parseLong(norma));
            cie.setEstado(1);
            crear(cie, idUser);
        } catch (BadRequestException e) {
            if (listCieError == null) {
                listCieError = new LinkedList<>();
            }
            setCieDtoError(e.getErrorRs().getMessage(), line, listCieError);
        }
    }

    @Override
    public Resource descargarArchivoNoCargado(String nombre) {
        return fileStorageService.loadFileAsResource(rutaFilesAlice + rutaFilesCie + File.separator + nombre);
    }

    @Override
    public List<CieDtoError> listarArchivosNoCargados(String nombre) {
        try {
            File archivo = fileStorageService.getFile(rutaFilesAlice + rutaFilesCie + File.separator + nombre);
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<CieDtoError> readValues
                    = mapper.reader(CieDtoError.class).with(bootstrapSchema).readValues(archivo);
            return readValues.readAll();
        } catch (IOException e) {
            throw new RuntimeException("Error cargando el archivo " + nombre, e);
        }
    }

}
