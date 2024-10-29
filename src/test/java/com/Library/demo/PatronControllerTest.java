package com.Library.demo;

import com.Library.demo.controller.PatronController;
import com.Library.demo.entity.Patron;
import com.Library.demo.service.PatronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllPatrons_ShouldReturnPatrons() throws Exception {
        Patron patron1 = new Patron(1L, "John Doe", "123456789", "123 Elm St");
        Patron patron2 = new Patron(2L, "Jane Smith", "987654321", "456 Oak St");

        when(patronService.getAllPatrons()).thenReturn(Arrays.asList(patron1, patron2));

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[1].name").value("Jane Smith"));
    }

    @Test
    public void getPatronById_ShouldReturnPatron() throws Exception {
        Patron patron = new Patron(1L, "John Doe", "123456789", "123 Elm St");

        when(patronService.getPatronById(1L)).thenReturn(patron);

        mockMvc.perform(get("/api/patrons/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void addPatron_ShouldCreatePatron() throws Exception {
        Patron newPatron = new Patron(null, "John Doe", "123456789", "123 Elm St");
        Patron savedPatron = new Patron(1L, "John Doe", "123456789", "123 Elm St");

        when(patronService.savePatron(any(Patron.class))).thenReturn(savedPatron);

        mockMvc.perform(post("/api/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPatron)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    public void updatePatron_ShouldUpdatePatron() throws Exception {
        Patron updatedPatron = new Patron(1L, "John Doe Updated", "123456789", "123 Elm St");

        when(patronService.savePatron(any(Patron.class))).thenReturn(updatedPatron);

        mockMvc.perform(put("/api/patrons/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatron)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe Updated"));
    }

    @Test
    public void deletePatron_ShouldDeletePatron() throws Exception {
        doNothing().when(patronService).deletePatron(1L);

        mockMvc.perform(delete("/api/patrons/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
