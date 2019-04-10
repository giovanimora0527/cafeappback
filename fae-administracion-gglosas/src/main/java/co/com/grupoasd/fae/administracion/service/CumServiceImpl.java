/*
* Archivo: CumServiceImpl.java
* Fecha: 19/02/2019
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
import co.com.grupoasd.fae.model.dto.CumDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cum;
import co.com.grupoasd.fae.model.repository.CumRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Servicios para Cum
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Service
public class CumServiceImpl implements CumService {

    private final CumRepository cumRepository;

    @Value("${cum.upload-dir}")
    private String rutaFilesCum;
    @Value("${alice.upload-dir}")
    private String rutaFilesAlice;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public CumServiceImpl(CumRepository cumRepository) {
        this.cumRepository = cumRepository;
    }

    @Transactional
    @Override
    public Cum crear(Cum cum, Long userId) {
        List<Cum> cumExiste = this.cumRepository.findByCodigoAndEstado(cum.getCodigo(), cum.getEstado());
        if (cumExiste != null && !cumExiste.isEmpty()) {
            cum.setId(cumExiste.get(0).getId());
            cum.setCreadoPor(cumExiste.get(0).getCreadoPor());
            cum.setFechaCreacion(cumExiste.get(0).getFechaCreacion());
            cum.setModificadoPor(userId);
            cum.setFechaModificacion(Date.from(Instant.now()));
        } else {
            cum.setCreadoPor(userId);
            cum.setFechaCreacion(Date.from(Instant.now()));
        }

        return cumRepository.save(cum);
    }

    private void setCumDtoError(String error, String[] line, List<CumDtoError> listCumError) {
        CumDtoError cumDtoError = new CumDtoError();
        cumDtoError.setLinea(Arrays.toString(line));
        cumDtoError.setDetalleError(error);
        listCumError.add(cumDtoError);
    }

    /**
     * Metodo que genera el archivo de error cuando un cum no carga
     *
     * @param listCumError
     * @param nombreArchivo
     * @throws IOException
     */
    private void guardarArchivoErrores(List<CumDtoError> listCumError, String nombreArchivo) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("linea", CsvSchema.ColumnType.STRING)
                .addColumn("detalleError", CsvSchema.ColumnType.STRING)
                .build().withHeader();
        schema = schema.withColumnSeparator(';');
        ObjectWriter myObjectWriter = mapper.writer(schema);
        File tempFile = new File(rutaFilesAlice + rutaFilesCum, nombreArchivo.concat(".csv"));
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
        myObjectWriter.writeValue(writerOutputStream, listCumError);
    }

    @Override
    public List<String> getArchivosNoCargados() {
        List<String> rutas = new LinkedList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(rutaFilesAlice + rutaFilesCum))) {
            for (Path path : directoryStream) {
                rutas.add(path.getFileName().toString());
            }
        } catch (IOException | DirectoryIteratorException ex) {
            throw new RuntimeException("Ocurrio un error interno");
        }
        return rutas;
    }

    @Override
    public GenericRs cargarArchivo(MultipartFile archivo, Long idUser) {
        boolean existeData = false;
        try {
            List<CumDtoError> listCumError = new LinkedList<>();
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
                    setCum(line, idUser, listCumError);
                }
            }
            csvReader.close();
            if (!listCumError.isEmpty()) {
                guardarArchivoErrores(listCumError, archivo.getOriginalFilename().substring(0, archivo.getOriginalFilename().lastIndexOf(".")).concat("_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
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

    private void setCum(String[] line, Long idUser, List<CumDtoError> listCumError) {
        try {
            if (line.length < 6) {
                setCumDtoError("Estructura archivo no cumple", line, listCumError);
                return;
            }
            if (StringUtils.isEmpty(line[0])) {
                setCumDtoError("Campo codigo no puede ser vacio", line, listCumError);
                return;
            }
            if (StringUtils.isEmpty(line[1])) {
                setCumDtoError("Campo nombre no puede ser vacio", line, listCumError);
                return;
            }
            if (line[0].length() > 11) {
                setCumDtoError("Campo codigo no cumple con la longitud maxima de 11", line, listCumError);
                return;
            } else if (line[1].length() > 255) {
                setCumDtoError("Campo nombre no cumple con la longitud maxima de 255", line, listCumError);
                return;
            } else if (line[1].trim().equals("")) {
                setCumDtoError("Campo nombre no puede estar vacío", line, listCumError);
                return;
            } else if (line[2].length() > 255) {
                setCumDtoError("Campo nombreComercial no cumple con la longitud maxima de 255", line, listCumError);
                return;
            } else if (line[2].trim().equals("")) {
                setCumDtoError("Campo nombreComercial no puede estar vacío", line, listCumError);
                return;
            } else if (line[3].length() > 6) {
                setCumDtoError("Campo concentracion no cumple con la longitud maxima de 6", line, listCumError);
                return;
            } else if (line[3].trim().equals("")) {
                setCumDtoError("Campo concentracion no puede estar vacío", line, listCumError);
                return;
            } else if (line[4].length() > 255) {
                setCumDtoError("Campo presentacion no cumple con la longitud maxima de 60", line, listCumError);
                return;
            } else if (line[4].trim().equals("")) {
                setCumDtoError("Campo presentacion no puede estar vacío", line, listCumError);
                return;
            } else if (line[5].length() > 10) {
                setCumDtoError("Campo fechaInactivo no cumple con la longitud maxima de 10", line, listCumError);
                return;
            }

            Cum cum = new Cum();
            cum.setCodigo(line[0].trim());
            cum.setNombre(line[1].trim());
            cum.setNombreComercial(line[2].trim());
            cum.setConcentracion(line[3].trim());
            cum.setPresentacion(line[4].trim());
            if (StringUtils.isEmpty(line[5])) {
                cum.setEstado(1);
            } else {
                cum.setFechaInactivo(Date.from(LocalDate.parse(line[5]).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                cum.setEstado(0);
            }

            crear(cum, idUser);
        } catch (BadRequestException e) {
            if (listCumError == null) {
                listCumError = new LinkedList<>();
            }
            setCumDtoError(e.getErrorRs().getMessage(), line, listCumError);
        } catch (DateTimeParseException e) {
            if (listCumError == null) {
                listCumError = new LinkedList<>();
            }
            setCumDtoError("Error en el formato de fecha, formato correcto(yyyy-MM-dd)", line, listCumError);
        } catch (Exception e) {
            if (listCumError == null) {
                listCumError = new LinkedList<>();
            }
            setCumDtoError("Ocurrio un error inesperado cargando el archivo", line, listCumError);
        }
    }

    @Override
    public Resource descargarArchivoNoCargado(String nombre) {
        return fileStorageService.loadFileAsResource(rutaFilesAlice + rutaFilesCum + File.separator + nombre);
    }

    @Override
    public List<CumDtoError> listarArchivosNoCargados(String nombre) {
        try {
            File archivo = fileStorageService.getFile(rutaFilesAlice + rutaFilesCum + File.separator + nombre);
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<CumDtoError> readValues
                    = mapper.reader(CumDtoError.class).with(bootstrapSchema).readValues(archivo);
            return readValues.readAll();
        } catch (IOException e) {
            throw new RuntimeException("Error cargando el archivo " + nombre, e);
        }
    }

}
