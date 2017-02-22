package com.bakery.tpv.web.rest;

import com.bakery.tpv.BakeryTpvApp;

import com.bakery.tpv.domain.LineaOferta;
import com.bakery.tpv.repository.LineaOfertaRepository;
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

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LineaOfertaResource REST controller.
 *
 * @see LineaOfertaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BakeryTpvApp.class)
public class LineaOfertaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CANTIDAD = 1;
    private static final Integer UPDATED_CANTIDAD = 2;

    @Autowired
    private LineaOfertaRepository lineaOfertaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLineaOfertaMockMvc;

    private LineaOferta lineaOferta;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
            LineaOfertaResource lineaOfertaResource = new LineaOfertaResource(lineaOfertaRepository);
        this.restLineaOfertaMockMvc = MockMvcBuilders.standaloneSetup(lineaOfertaResource)
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
    public static LineaOferta createEntity(EntityManager em) {
        LineaOferta lineaOferta = new LineaOferta()
                .nombre(DEFAULT_NOMBRE)
                .cantidad(DEFAULT_CANTIDAD);
        return lineaOferta;
    }

    @Before
    public void initTest() {
        lineaOferta = createEntity(em);
    }

    @Test
    @Transactional
    public void createLineaOferta() throws Exception {
        int databaseSizeBeforeCreate = lineaOfertaRepository.findAll().size();

        // Create the LineaOferta

        restLineaOfertaMockMvc.perform(post("/api/linea-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineaOferta)))
            .andExpect(status().isCreated());

        // Validate the LineaOferta in the database
        List<LineaOferta> lineaOfertaList = lineaOfertaRepository.findAll();
        assertThat(lineaOfertaList).hasSize(databaseSizeBeforeCreate + 1);
        LineaOferta testLineaOferta = lineaOfertaList.get(lineaOfertaList.size() - 1);
        assertThat(testLineaOferta.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testLineaOferta.getCantidad()).isEqualTo(DEFAULT_CANTIDAD);
    }

    @Test
    @Transactional
    public void createLineaOfertaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lineaOfertaRepository.findAll().size();

        // Create the LineaOferta with an existing ID
        LineaOferta existingLineaOferta = new LineaOferta();
        existingLineaOferta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLineaOfertaMockMvc.perform(post("/api/linea-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingLineaOferta)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<LineaOferta> lineaOfertaList = lineaOfertaRepository.findAll();
        assertThat(lineaOfertaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLineaOfertas() throws Exception {
        // Initialize the database
        lineaOfertaRepository.saveAndFlush(lineaOferta);

        // Get all the lineaOfertaList
        restLineaOfertaMockMvc.perform(get("/api/linea-ofertas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(lineaOferta.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].cantidad").value(hasItem(DEFAULT_CANTIDAD)));
    }

    @Test
    @Transactional
    public void getLineaOferta() throws Exception {
        // Initialize the database
        lineaOfertaRepository.saveAndFlush(lineaOferta);

        // Get the lineaOferta
        restLineaOfertaMockMvc.perform(get("/api/linea-ofertas/{id}", lineaOferta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(lineaOferta.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.cantidad").value(DEFAULT_CANTIDAD));
    }

    @Test
    @Transactional
    public void getNonExistingLineaOferta() throws Exception {
        // Get the lineaOferta
        restLineaOfertaMockMvc.perform(get("/api/linea-ofertas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLineaOferta() throws Exception {
        // Initialize the database
        lineaOfertaRepository.saveAndFlush(lineaOferta);
        int databaseSizeBeforeUpdate = lineaOfertaRepository.findAll().size();

        // Update the lineaOferta
        LineaOferta updatedLineaOferta = lineaOfertaRepository.findOne(lineaOferta.getId());
        updatedLineaOferta
                .nombre(UPDATED_NOMBRE)
                .cantidad(UPDATED_CANTIDAD);

        restLineaOfertaMockMvc.perform(put("/api/linea-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLineaOferta)))
            .andExpect(status().isOk());

        // Validate the LineaOferta in the database
        List<LineaOferta> lineaOfertaList = lineaOfertaRepository.findAll();
        assertThat(lineaOfertaList).hasSize(databaseSizeBeforeUpdate);
        LineaOferta testLineaOferta = lineaOfertaList.get(lineaOfertaList.size() - 1);
        assertThat(testLineaOferta.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testLineaOferta.getCantidad()).isEqualTo(UPDATED_CANTIDAD);
    }

    @Test
    @Transactional
    public void updateNonExistingLineaOferta() throws Exception {
        int databaseSizeBeforeUpdate = lineaOfertaRepository.findAll().size();

        // Create the LineaOferta

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLineaOfertaMockMvc.perform(put("/api/linea-ofertas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lineaOferta)))
            .andExpect(status().isCreated());

        // Validate the LineaOferta in the database
        List<LineaOferta> lineaOfertaList = lineaOfertaRepository.findAll();
        assertThat(lineaOfertaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLineaOferta() throws Exception {
        // Initialize the database
        lineaOfertaRepository.saveAndFlush(lineaOferta);
        int databaseSizeBeforeDelete = lineaOfertaRepository.findAll().size();

        // Get the lineaOferta
        restLineaOfertaMockMvc.perform(delete("/api/linea-ofertas/{id}", lineaOferta.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LineaOferta> lineaOfertaList = lineaOfertaRepository.findAll();
        assertThat(lineaOfertaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LineaOferta.class);
    }
}
