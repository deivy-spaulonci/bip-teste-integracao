import { Injectable } from '@angular/core';
import {DefaultService} from './default.service';
import {Observable} from 'rxjs';
import {Beneficio} from '../model/beneficio';
import { TransferRequestDTO } from '../model/transferRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class BenificioService {
  ROOT:string="beneficios";

  constructor(private defaultService:DefaultService) {
  }

  getBeneficioPage(url:string): Observable<any>{
    return this.defaultService.get(this.ROOT+url);
  }

  getBeneficioById(id:number): Observable<Beneficio>{
    return this.defaultService.get(this.ROOT+'/'+id);
  }

  delBeneficio(id:number):Observable<any>{
    return this.defaultService.delete(id,this.ROOT)
  }

  update(id:number,beneficio:Beneficio): Observable<Beneficio>{
    return this.defaultService.update(beneficio, this.ROOT+'/'+id)
  }

  create(beneficio:Beneficio): Observable<Beneficio>{
    return this.defaultService.save(beneficio, this.ROOT)
  }

  transfer(transferencia:TransferRequestDTO): Observable<any>{
    console.log(`${this.ROOT}/transfer`);
    return this.defaultService.post(transferencia, this.ROOT + '/transfer');
  }
}
