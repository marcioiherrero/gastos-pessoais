package com.example.meusgastos.domain.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.meusgastos.domain.Enum.ETipoTitulo;
import com.example.meusgastos.dto.dashboard.DashboardResponseDto;
import com.example.meusgastos.dto.titulo.TituloResponseDto;

@Service
public class DashboardService {
    
    @Autowired
    private TituloService tituloService;
    
    public DashboardResponseDto obterFluxoDeCaixa(String periodoInicial, String periodoFinal) {
        
        List<TituloResponseDto> titulos = tituloService.obterPorDataVencimento(periodoInicial, periodoFinal);
        
        Double totalAPagar = 0.0;
        Double totalAReceber = 0.0;
        Double saldo = 0.0;
        List<TituloResponseDto> titulosAPagar = new ArrayList<>();
        List<TituloResponseDto> titulosAReceber = new ArrayList<>();
        
        for (TituloResponseDto titulo : titulos) {
            
            if (titulo.getTipo() == ETipoTitulo.APAGAR) {
                totalAPagar += titulo.getValor();
                titulosAPagar.add(titulo);
            } else {
                totalAReceber += titulo.getValor();
                titulosAReceber.add(titulo);
            }
        }
        
        saldo = totalAReceber - totalAPagar;
        
        return new DashboardResponseDto(totalAPagar, totalAReceber, saldo, titulosAPagar, titulosAReceber);
    }
}
