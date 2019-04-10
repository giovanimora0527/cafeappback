/*
* Archivo: CupsServiceImpl.java
* Fecha: 19/12/2018
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
import co.com.grupoasd.fae.model.dto.CupsDtoError;
import co.com.grupoasd.fae.model.dto.GenericRs;
import co.com.grupoasd.fae.model.entity.Cups;
import co.com.grupoasd.fae.model.entity.Entidad;
import co.com.grupoasd.fae.model.repository.CupsRepository;
import co.com.grupoasd.fae.model.repository.EntidadRepository;
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
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @inheritDoc
 *
 * @author Jose Moreno josem.moreno@grupoasd.com.co
 */
@Service
public class CupsServiceImpl implements CupsService {

    private final CupsRepository cupsRepository;
    private final EntidadRepository entidadRepository;

    @Value("${cups.upload-dir}")
    private String rutaFilesCups;
    @Value("${alice.upload-dir}")
    private String rutaFilesAlice;
    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    public CupsServiceImpl(final CupsRepository cupsRepository, final EntidadRepository entidadRepository) {
        this.cupsRepository = cupsRepository;
        this.entidadRepository = entidadRepository;
    }

    /**
     * Crea un registro de CUPS
     *
     * @param cups objeto CUPS
     * @param userId id usuario que realiza el cargue
     * @param masivo bandera que indica si la creacion viene desde el cargue
     * masivo
     * @return objeto CUPS creado
     */
    @Transactional
    @Override
    public Cups crear(Cups cups, Long userId, boolean masivo) {
        Cups cupsExiste = cupsRepository.findByCodigoAndNorma(cups.getCodigo(), cups.getNorma());
        if (cupsExiste != null) {
            if (masivo) {
                cups.setId(cupsExiste.getId());
                return editar(cups, userId, masivo);
            } else {
                throw new BadRequestException(ErrorRs.get("No se puede crear el CUPS",
                        "El código " + cups.getCodigo() + " ya existe.", 400));
            }
        } else {
            List<Cups> cupsList = cupsRepository.findByDetalleAndNorma(cups.getDetalle(), cups.getNorma());
            if (cupsList != null && !cupsList.isEmpty()) {
                String msg;
                if (!masivo) {
                    msg = "La descripción ya está asociada a un CUPS interno " + cupsList.get(0).getCodigo();
                } else {
                    msg = "La descripción ya está asociada al CUPS " + cupsList.get(0).getCodigo();
                }
                throw new BadRequestException(ErrorRs.get("No se puede crear el CUPS", msg, 400));
            }
            cups.setCreadoPor(userId);
            cups.setFechaCreacion(Date.from(Instant.now()));
        }

        return cupsRepository.save(cups);
    }

    @Override
    public Cups editar(Cups cups, Long userId, boolean masivo) {
        if (cups.getId() != null) {
            Optional<Cups> cupsExiste = cupsRepository.findById(cups.getId());
            if (cupsExiste.isPresent()) {
                Cups cups1 = cupsExiste.get();
                Optional<Entidad> entiOptional = entidadRepository.findById(cups.getNorma());
                if (entiOptional.isPresent()) {
                    Cups cupsNorma = cupsRepository.findByCodigoAndNorma(cups.getCodigo(), cups.getNorma());
                    if (cupsNorma != null) {
                        if (!cups.getId().equals(cupsNorma.getId())) {
                            throw new BadRequestException(ErrorRs.get("No se puede editar el CUPS",
                                    "El código " + cups.getCodigo() + " ya está asociado a la misma norma.", 400));
                        }
                    }
                } else {
                    if (!masivo) {
                        throw new BadRequestException(ErrorRs.get("No se puede modificar el CUPS",
                                "El CUPS no se puede modificar.", 400));
                    }
                }
                List<Cups> cupsList = cupsRepository.findByDetalleAndNorma(cups.getDetalle(), cups.getNorma());
                if (cupsList != null && !cupsList.isEmpty()) {
                    String msg;
                    if (!masivo) {
                        msg = "La descripción ya está asociada a un CUPS interno " + cupsList.get(0).getCodigo();
                    } else {
                        msg = "La descripción ya está asociada al CUPS " + cupsList.get(0).getCodigo();
                    }
                    throw new BadRequestException(ErrorRs.get("No se puede crear el CUPS", msg, 400));
                }
                cups.setCreadoPor(cups1.getCreadoPor());
                cups.setFechaCreacion(cups1.getFechaCreacion());
                cups.setModificadoPor(userId);
                cups.setFechaModificacion(Date.from(Instant.now()));
                return cupsRepository.save(cups);
            }
        }
        throw new NotFoundException(ErrorRs.get("No existe el cups a modificar", null, 404));
    }

    /**
     * Obtiene una lista de nombres de archivos no cargados
     *
     * @return una lista de nombres de los archivos
     */
    @Override
    public List<String> getArchivosNoCargados() {
        List<String> rutas = new LinkedList<>();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(rutaFilesAlice + rutaFilesCups))) {
            for (Path path : directoryStream) {
                rutas.add(path.getFileName().toString());
            }
        } catch (IOException | DirectoryIteratorException ex) {
            throw new RuntimeException("Ocurrio un error interno");
        }
        return rutas;
    }

    /**
     * Hace el cargue de un archivo csv y guarda en base los registros que estan
     * correctos y los que no genera un archivo de error y lo guarda en una ruta
     * parametrizable
     *
     * @param archivo archivo a cargar
     * @param idUser id usuario que realiza el cargue
     * @return Respuesta generica confirmando la transacción
     */
    @Transactional
    @Override
    public GenericRs cargarArchivo(MultipartFile archivo, Long idUser, String norma) {
        boolean existeData = false;
        try {
            List<CupsDtoError> listCupsError = new LinkedList<>();
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
                    setCups(line, idUser, listCupsError, norma);
                }
            }
            csvReader.close();
            if (!listCupsError.isEmpty()) {
                guardarArchivoErrores(listCupsError, archivo.getOriginalFilename().substring(0, archivo.getOriginalFilename().lastIndexOf(".")).concat("_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
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

    private void setCups(String[] line, Long idUser, List<CupsDtoError> listCupsError, String norma) {
        try {
            if (line.length < 3) {
                setCupsDtoError("Campos vacios o incompletos", line, listCupsError);
                return;
            }
            if (org.apache.commons.lang3.StringUtils.isEmpty(line[0])) {
                setCupsDtoError("Campo codigo no puede ser vacio", line, listCupsError);
                return;
            }
            if (org.apache.commons.lang3.StringUtils.isEmpty(line[1])) {
                setCupsDtoError("Campo detalle no puede ser vacio", line, listCupsError);
                return;
            }
            if (line[0].trim().length() > 6) {
                setCupsDtoError("Campo codigo no cumple con la longitud maxima de 6", line, listCupsError);
                return;
            } else if (line[1].trim().length() > 255) {
                setCupsDtoError("Campo detalle no cumple con la longitud maxima de 255", line, listCupsError);
                return;
            } else if (line[2].length() != 10) {
                setCupsDtoError("Campo fechaInicioVigencia no cumple con la longitud de 10", line, listCupsError);
                return;
            }
            Cups cups = new Cups();
            cups.setCodigo(line[0].trim());
            cups.setDetalle(line[1].trim());
            cups.setFechaInicioVigencia(Date.from(LocalDate.parse(line[2]).atStartOfDay(ZoneId.systemDefault()).toInstant()));
            cups.setNorma(norma);
            cups.setEstado(1);
            crear(cups, idUser, true);
        } catch (BadRequestException e) {
            if (listCupsError == null) {
                listCupsError = new LinkedList<>();
            }
            setCupsDtoError(e.getErrorRs().getMessage(), line, listCupsError);
        } catch (DateTimeParseException e) {
            if (listCupsError == null) {
                listCupsError = new LinkedList<>();
            }
            setCupsDtoError("Error en el formato de fecha, formato correcto(yyyy-MM-dd)", line, listCupsError);
        } catch (Exception e) {
            if (listCupsError == null) {
                listCupsError = new LinkedList<>();
            }
            setCupsDtoError("Ocurrio un error inesperado cargando el archivo", line, listCupsError);
        }
    }

    private void setCupsDtoError(String error, String[] line, List<CupsDtoError> listCupsError) {
        CupsDtoError cupsDtoError = new CupsDtoError();
        cupsDtoError.setLinea(Arrays.toString(line));
        cupsDtoError.setDetalleError(error);
        listCupsError.add(cupsDtoError);
    }

    private void guardarArchivoErrores(List<CupsDtoError> listCupsError, String nombreArchivo) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("linea", CsvSchema.ColumnType.STRING)
                .addColumn("detalleError", CsvSchema.ColumnType.STRING)
                .build().withHeader();
        schema = schema.withColumnSeparator(';');
        ObjectWriter myObjectWriter = mapper.writer(schema);
        File tempFile = new File(rutaFilesAlice + rutaFilesCups, nombreArchivo.concat(".csv"));
        FileOutputStream tempFileOutputStream = new FileOutputStream(tempFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(tempFileOutputStream, 1024);
        OutputStreamWriter writerOutputStream = new OutputStreamWriter(bufferedOutputStream, "UTF-8");
        myObjectWriter.writeValue(writerOutputStream, listCupsError);
    }

    /**
     * Descarga un archivo de errores de cargue de CUPS
     *
     * @param nombre
     * @return Descarga Archivo de errores
     */
    @Override
    public Resource descargarArchivoNoCargado(String nombre) {
        return fileStorageService.loadFileAsResource(rutaFilesAlice + rutaFilesCups + File.separator + nombre);
    }

    /**
     * Lista un archivo de errores de cargue de CUPS
     *
     * @param nombre
     * @return Lista Archivo de errores
     */
    @Override
    public List<CupsDtoError> listarArchivosNoCargados(String nombre) {
        try {
            File archivo = fileStorageService.getFile(rutaFilesAlice + rutaFilesCups + File.separator + nombre);
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            MappingIterator<CupsDtoError> readValues
                    = mapper.reader(CupsDtoError.class).with(bootstrapSchema).readValues(archivo);
            return readValues.readAll();
        } catch (Exception e) {
            throw new RuntimeException("Error cargando el archivo " + nombre, e);
        }
    }

}
