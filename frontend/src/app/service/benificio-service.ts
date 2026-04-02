import { Injectable } from '@angular/core';
import {DefaultService} from './default.service';
import {Observable} from 'rxjs';
import {Beneficio} from '../model/beneficio';

@Injectable({
  providedIn: 'root',
})
export class BenificioService {
  ROOT:string="beneficio";

  constructor(private defaultService:DefaultService) {
  }

  getBeneficioPage(url:string): Observable<any>{
    return this.defaultService.get(this.ROOT+'/page'+url);
  }

  getBeneficioById(id:number): Observable<Beneficio>{
    return this.defaultService.get(this.ROOT+'/'+id);
  }

  delBeneficio(id:number):Observable<any>{
    return this.defaultService.delete(id,this.ROOT)
  }

  update(beneficio:Beneficio): Observable<Beneficio>{
    return this.defaultService.update(beneficio, this.ROOT)
  }

  create(beneficio:Beneficio): Observable<Beneficio>{
    return this.defaultService.save(beneficio, this.ROOT)
  }
}
