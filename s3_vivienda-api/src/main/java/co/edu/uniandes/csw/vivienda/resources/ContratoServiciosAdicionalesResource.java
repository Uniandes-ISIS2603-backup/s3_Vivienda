/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.vivienda.resources;


import co.edu.uniandes.csw.vivienda.dtos.ServicioAdicionalDetailDTO;
import co.edu.uniandes.csw.vivienda.ejb.ContratoServiciosAdicionalesLogic;
import co.edu.uniandes.csw.vivienda.ejb.ServicioAdicionalLogic;
import co.edu.uniandes.csw.vivienda.entities.ServicioAdicionalEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Paula Molina Ruiz
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContratoServiciosAdicionalesResource {
    
private static final Logger LOGGER = Logger.getLogger(ContratoServiciosAdicionalesResource.class.getName());

    @Inject
    private ContratoServiciosAdicionalesLogic contratoServiciosAdicionalesLogic;

    @Inject
    private ServicioAdicionalLogic servicioAdicionalLogic;

    /**
     * Asocia un autor existente con un libro existente
     *
     * @param servicioAdicionalId El ID del autor que se va a asociar
     * @param viviendaId El ID del libro al cual se le va a asociar el autor
     * @param booksId El ID del libro al cual se le va a asociar el autor
     * @return JSON {@link AuthorDetailDTO} - El autor asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @POST
    @Path("{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDetailDTO addAuthor(@PathParam("booksId") Long booksId, @PathParam("viviendaId") Long viviendaId, @PathParam("servicioAdicionalId") Long servicioAdicionalId) {
        LOGGER.log(Level.INFO, "BookAuthorsResource addAuthor: input: booksId {0} , viviendaId: {0} , servicioAdicionalId {1}", new Object[]{booksId, servicioAdicionalId});
        if (servicioAdicionalLogic.getServicioAdicional(viviendaId, servicioAdicionalId) == null) {
            throw new WebApplicationException("El recurso /authors/" + servicioAdicionalId + " no existe.", 404);
        }
        ServicioAdicionalDetailDTO detailDTO = new ServicioAdicionalDetailDTO(contratoServiciosAdicionalesLogic.addServicioAdicional(booksId, servicioAdicionalId, viviendaId));
        LOGGER.log(Level.INFO, "BookAuthorsResource addAuthor: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los autores que existen en un libro.
     *
     * @param booksId El ID del libro del cual se buscan los autores
     * @return JSONArray {@link AuthorDetailDTO} - Los autores encontrados en el
     * libro. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ServicioAdicionalDetailDTO> getAuthors(@PathParam("booksId") Long booksId) {
        LOGGER.log(Level.INFO, "BookAuthorsResource getAuthors: input: {0}", booksId);
        List<ServicioAdicionalDetailDTO> lista = authorsListEntity2DTO(contratoServiciosAdicionalesLogic.getServiciosAdicionales(booksId));
        LOGGER.log(Level.INFO, "BookAuthorsResource getAuthors: output: {0}", lista.toString());
        return lista;
    }

    /**
     * Busca y devuelve el autor con el ID recibido en la URL, relativo a un
     * libro.
     *
     * @param servicioAdicionalId El ID del autor que se busca
     * @param viviendaId El ID del libro al cual se le va a asociar la lista deautores
     * @param booksId El ID del libro del cual se busca el autor
     * @return {@link AuthorDetailDTO} - El autor encontrado en el libro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @GET
    @Path("{servicioAdicionalId: \\d+}")
    public ServicioAdicionalDetailDTO getAuthor(@PathParam("booksId") Long booksId, @PathParam("servicioAdicionalId") Long servicioAdicionalId, @PathParam("viviendaId") Long viviendaId ) {
        LOGGER.log(Level.INFO, "BookAuthorsResource getAuthor: input: booksId {0} , viviendaId: {0} , authorsId {1}", new Object[]{booksId, servicioAdicionalId});
        if (servicioAdicionalLogic.getServicioAdicional( viviendaId ,servicioAdicionalId) == null) {
            throw new WebApplicationException("El recurso /authors/" + servicioAdicionalId + " no existe.", 404);
        }
        ServicioAdicionalDetailDTO detailDTO = new ServicioAdicionalDetailDTO(contratoServiciosAdicionalesLogic.getServicioAdicional(booksId, servicioAdicionalId, viviendaId));
        LOGGER.log(Level.INFO, "BookAuthorsResource getAuthor: output: {0}", detailDTO.toString());
        return detailDTO;
    }

    /**
     * Actualiza la lista de autores de un libro con la lista que se recibe en
     * el cuerpo.
     *
     * @param booksId El ID del libro al cual se le va a asociar la lista deautores
     * @param viviendaId El ID del libro al cual se le va a asociar la lista deautores
     * @param authors JSONArray {@link AuthorDetailDTO} - La lista de autores
     * que se desea guardar.
     * @return JSONArray {@link AuthorDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @PUT
    public List<ServicioAdicionalDetailDTO> replaceAuthors(@PathParam("booksId") Long booksId, @PathParam("viviendaId") Long viviendaId, List<ServicioAdicionalDetailDTO> authors) {
        LOGGER.log(Level.INFO, "BookAuthorsResource replaceAuthors: input: booksId {0} , viviendaId {0} , authors {1}", new Object[]{booksId, authors.toString()});
        for (ServicioAdicionalDetailDTO author : authors) {
            if (servicioAdicionalLogic.getServicioAdicional( author.getVivienda().getId(), author.getId()) == null) {
                throw new WebApplicationException("El recurso /authors/" + author.getId() + " no existe.", 404);
            }
        }
        List<ServicioAdicionalDetailDTO> lista = authorsListEntity2DTO(contratoServiciosAdicionalesLogic.replaceServiciosAdicionales(booksId, authorsListDTO2Entity(authors)));
        LOGGER.log(Level.INFO, "BookAuthorsResource replaceAuthors: output:{0}", lista.toString());
        return lista;
    }

    /**
     * Elimina la conexión entre el autor y el libro recibidos en la URL.
     *
     * @param booksId El ID del libro al cual se le va a desasociar el autor
     * @param viviendaId El ID del libro al cual se le va a asociar el autor
     * @param servicioAdicionalId El ID del autor que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el autor.
     */
    @DELETE
    @Path("{servicioAdicionalId: \\d+}")
    public void removeAuthor(@PathParam("booksId") Long booksId, @PathParam("servicioAdicionalId") Long servicioAdicionalId, @PathParam("viviendaId") Long viviendaId) {
        LOGGER.log(Level.INFO, "BookAuthorsResource removeAuthor: input: booksId {0} , viviendaId {0} , servicioAdicionalId {1}", new Object[]{booksId, servicioAdicionalId});
        if (servicioAdicionalLogic.getServicioAdicional(viviendaId ,servicioAdicionalId) == null) {
            throw new WebApplicationException("El recurso /authors/" + servicioAdicionalId + " no existe.", 404);
        }
        contratoServiciosAdicionalesLogic.removeServicioAdicional(booksId, servicioAdicionalId, viviendaId);
        LOGGER.info("BookAuthorsResource removeAuthor: output: void");
    }

    /**
     * Convierte una lista de AuthorEntity a una lista de AuthorDetailDTO.
     *
     * @param entityList Lista de AuthorEntity a convertir.
     * @return Lista de AuthorDetailDTO convertida.
     */
    private List<ServicioAdicionalDetailDTO> authorsListEntity2DTO(List<ServicioAdicionalEntity> entityList) {
        List<ServicioAdicionalDetailDTO> list = new ArrayList<>();
        for (ServicioAdicionalEntity entity : entityList) {
            list.add(new ServicioAdicionalDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de AuthorDetailDTO a una lista de AuthorEntity.
     *
     * @param dtos Lista de AuthorDetailDTO a convertir.
     * @return Lista de AuthorEntity convertida.
     */
    private List<ServicioAdicionalEntity> authorsListDTO2Entity(List<ServicioAdicionalDetailDTO> dtos) {
        List<ServicioAdicionalEntity> list = new ArrayList<>();
        for (ServicioAdicionalDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
