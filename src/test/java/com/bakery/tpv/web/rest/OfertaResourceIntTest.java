package com.bakery.tpv.web.rest;

import com.bakery.tpv.BakeryTpvApp;

import com.bakery.tpv.domain.Oferta;
import com.bakery.tpv.repository.OfertaRepository;
import com.bakery.tpv.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.bakery.tpv.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OfertaResource REST controller.
 *
 * @see OfertaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BakeryTpvApp.class)
public class OfertaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGEN = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGEN = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGEN_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGEN_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    private static final ZonedDateTime DEFAULT_FECHAINICIO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHAINICIO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_FECHAFINAL = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FECHAFINAL = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private OfertaRepository ofertaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOfertaMockMvc;

    private Oferta oferta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            OfertaResource ofertaResource = new OfertaResource(ofertaRepository);
        this.restOfertaMockMvc = MockMvcBuilders.standaloneSetup(ofertaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Oferta createEntity(EntityManager em) {
        Oferta oferta = new Oferta()
                .nombre(DEFAULT_NOMBRE)
                .imagen(DEFAULT_IMAGEN)
                .imagenContentType(DEFAULT_IMAGEN_CONTENT_TYPE)
                .descripcion(DEFAULT_DESCRIPCION)
                .precio(DEFAULT_PRECIO)
                .fechainicio(DEFAULT_FECHAINICIO)
                .fechafinal(DEFAULT_FECHAFINAL);
        return oferta;
    }

    @Before
    public void initTest() {
        oferta = createEntity(em);
    }

    @Test
    @Transactional
    public void createOferta() throws Exception {
        int databaseSizeBeforeCreate = ofertaRepository.findAll().size();

        // Create the Oferta

        restOfertaMockMvc.perform(post("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oferta)))
            .andExpect(status().isCreated());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeCreate + 1);
        Oferta testOferta = ofertaList.get(ofertaList.size() - 1);
        assertThat(testOferta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testOferta.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testOferta.getImagenContentType()).isEqualTo(DEFAULT_IMAGEN_CONTENT_TYPE);
        assertThat(testOferta.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testOferta.getPrecio()).isEqualTo(DEFAULT_PRECIO);
        assertThat(testOferta.getFechainicio()).isEqualTo(DEFAULT_FECHAINICIO);
        assertThat(testOferta.getFechafinal()).isEqualTo(DEFAULT_FECHAFINAL);
    }

    @Test
    @Transactional
    public void createOfertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ofertaRepository.findAll().size();

        // Create the Oferta with an existing ID
        Oferta existingOferta = new Oferta();
        existingOferta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfertaMockMvc.perform(post("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingOferta)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOfertas() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);

        // Get all the ofertaList
        restOfertaMockMvc.perform(get("/api/ofertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(oferta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].imagenContentType").value(hasItem(DEFAULT_IMAGEN_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGEN))))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())))
            .andExpect(jsonPath("$.[*].fechainicio").value(hasItem(sameInstant(DEFAULT_FECHAINICIO))))
            .andExpect(jsonPath("$.[*].fechafinal").value(hasItem(sameInstant(DEFAULT_FECHAFINAL))));
    }

    @Test
    @Transactional
    public void getOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);

        // Get the oferta
        restOfertaMockMvc.perform(get("/api/ofertas/{id}", oferta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(oferta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.imagenContentType").value(DEFAULT_IMAGEN_CONTENT_TYPE))
            .andExpect(jsonPath("$.imagen").value(Base64Utils.encodeToString(DEFAULT_IMAGEN)))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()))
            .andExpect(jsonPath("$.fechainicio").value(sameInstant(DEFAULT_FECHAINICIO)))
            .andExpect(jsonPath("$.fechafinal").value(sameInstant(DEFAULT_FECHAFINAL)));
    }

    @Test
    @Transactional
    public void getNonExistingOferta() throws Exception {
        // Get the oferta
        restOfertaMockMvc.perform(get("/api/ofertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);
        int databaseSizeBeforeUpdate = ofertaRepository.findAll().size();

        // Update the oferta
        Oferta updatedOferta = ofertaRepository.findOne(oferta.getId());
        updatedOferta
                .nombre(UPDATED_NOMBRE)
                .imagen(UPDATED_IMAGEN)
                .imagenContentType(UPDATED_IMAGEN_CONTENT_TYPE)
                .descripcion(UPDATED_DESCRIPCION)
                .precio(UPDATED_PRECIO)
                .fechainicio(UPDATED_FECHAINICIO)
                .fechafinal(UPDATED_FECHAFINAL);

        restOfertaMockMvc.perform(put("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOferta)))
            .andExpect(status().isOk());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeUpdate);
        Oferta testOferta = ofertaList.get(ofertaList.size() - 1);
        assertThat(testOferta.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testOferta.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testOferta.getImagenContentType()).isEqualTo(UPDATED_IMAGEN_CONTENT_TYPE);
        assertThat(testOferta.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testOferta.getPrecio()).isEqualTo(UPDATED_PRECIO);
        assertThat(testOferta.getFechainicio()).isEqualTo(UPDATED_FECHAINICIO);
        assertThat(testOferta.getFechafinal()).isEqualTo(UPDATED_FECHAFINAL);
    }

    @Test
    @Transactional
    public void updateNonExistingOferta() throws Exception {
        int databaseSizeBeforeUpdate = ofertaRepository.findAll().size();

        // Create the Oferta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOfertaMockMvc.perform(put("/api/ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(oferta)))
            .andExpect(status().isCreated());

        // Validate the Oferta in the database
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOferta() throws Exception {
        // Initialize the database
        ofertaRepository.saveAndFlush(oferta);
        int databaseSizeBeforeDelete = ofertaRepository.findAll().size();

        // Get the oferta
        restOfertaMockMvc.perform(delete("/api/ofertas/{id}", oferta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Oferta> ofertaList = ofertaRepository.findAll();
        assertThat(ofertaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Oferta.class);
    }
}
