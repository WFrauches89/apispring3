package com.meuscursos.apirestspring3.model;

import com.meuscursos.apirestspring3.dto.enderecos.EnderecoDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Endereco {

   private String logradouro;
   private String numero;
   private String complemento;
   private String bairro;
   private String uf;
   private String cep;
   private String cidade;


   public Endereco(EnderecoDTO dadosEnd) {
      this.logradouro = dadosEnd.logradouro();
      this.numero = dadosEnd.numero();
      this.complemento = dadosEnd.complemento();
      this.bairro= dadosEnd.bairro();
      this.uf= dadosEnd.uf();
      this.cep = dadosEnd.cep();
      this.cidade = dadosEnd.cidade();
   }

    public void atualizarEndereco(EnderecoDTO dados) {
      if(dados != null){
         this.logradouro = dados.logradouro();
         this.bairro= dados.bairro();
         this.uf= dados.uf();
         this.cep = dados.cep();
         this.cidade = dados.cidade();
         if (dados.numero() != null) {
            this.numero = dados.numero();
         }
         if (dados.complemento() != null) {
            this.complemento = dados.complemento();
         }
      }
    }
}
