import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';

@Component({
  selector: 'app-envio-texto',
  imports: [FormsModule],
  templateUrl: './envio-texto.html',
  styleUrl: './envio-texto.css',
})

export class EnvioTexto {

    texto ='';

    constructor(private http: HttpClient) {}

    enviar() {

      let texto = this.texto;

      if(texto === null){
        alert('O campo de texto não pode estar vazio!');
        return;
      }else if(texto.length > 40){
        alert('O texto não pode conter mais de 40 caracteres!');
        return;
      }else{
        alert('Texto enviado com sucesso!');
        }

      console.log('Tamanho:', texto.length);

      this.http.post(
        'http://localhost:8080/pedidos',
        texto,
        {
          headers: { 'Content-Type': 'text/plain' },
          responseType: 'text'
        }
      ).subscribe(res => {
        console.log('Resposta:', res);
        alert('Texto enviado com sucesso!');
        this.texto = '';
      });

    }
  }
