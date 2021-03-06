package es.ejercicio.microservicios.biblioteca.control;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import es.ejercicio.microservicios.biblioteca.cliente.ClienteCategorias;
import es.ejercicio.microservicios.dto.CategoriaDTO;


@Component
public class ControlCategorias {

	@Autowired
	ClienteCategorias clienteCategorias;

	@HystrixCommand(fallbackMethod="failObtenerCategorias")
	public List<CategoriaDTO> obtenerCategorias() {
		return clienteCategorias.obtenerCategorias();
	}

	@HystrixCommand(fallbackMethod="failObtenerCategoria")
	public CategoriaDTO obtenerCategoria(String id) {
		return clienteCategorias.obtenerCategoria(id);
	}


	@HystrixCommand(fallbackMethod="failNuevaCategoria")
    public ResponseEntity<CategoriaDTO>  nuevaCategoria(@RequestBody CategoriaDTO input) {
	   return clienteCategorias.nuevaCategoria(input);
   }

	@HystrixCommand(fallbackMethod="failEliminarCategoria")
	public void eliminarCategoria(String id) {
		clienteCategorias.eliminarCategoria(id);
	}

	public List<CategoriaDTO> failObtenerCategorias(Throwable t) {
        return new ArrayList<CategoriaDTO>();
    }

	public CategoriaDTO failObtenerCategoria(String id, Throwable t) {
        return CategoriaDTO.builder().id(0).nombre("NO DISPONIBLE").build();
    }

	public ResponseEntity<CategoriaDTO> failNuevaCategoria(CategoriaDTO input, Throwable t) {
       return ResponseEntity
        		.status(HttpStatus.NOT_FOUND)
        		.body(CategoriaDTO.builder().id(0).nombre("NO DISPONIBLE").build());
    }

	public void failEliminarCategoria(String id, Throwable t) {
		t.printStackTrace();
    }

}
