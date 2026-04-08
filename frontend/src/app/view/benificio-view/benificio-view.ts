import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DefaultService } from '../../service/default.service';
import { BenificioService } from '../../service/benificio-service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
  FormsModule,
} from '@angular/forms';
import { Beneficio } from '../../model/beneficio';
import { TransferRequestDTO } from '../../model/transferRequestDTO';

@Component({
  selector: 'app-benificio-view',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './benificio-view.html',
  styleUrl: './benificio-view.css',
})

export class BenificioView implements OnInit {
  beneficioResp$!: Observable<any>;
  form!: FormGroup;
  edicao: boolean = false;
  idEdicao: number = 0;
  transferncia!: TransferRequestDTO;
  selecionados: any[] = [];

  constructor(
    private beneficioService: BenificioService,
    private cdr: ChangeDetectorRef,
    private fb: FormBuilder,
  ) {}

  ngOnInit(): void {
    this.transferncia = new TransferRequestDTO();
    this.form = this.fb.group({
      nome: ['', [Validators.required, Validators.maxLength(100)]],
      descricao: ['', [Validators.required]],
      valor: [0, [Validators.required, Validators.min(0)]],
      ativo: [true],
      version: [0],
    });

    this.loadBeneficio();
  }

  loadBeneficio() {
    this.beneficioResp$ = this.beneficioService.getBeneficioPage('');
  }

  toggleSelecionado(item: any) {
    if (this.selecionados.includes(item)) {
      this.selecionados = this.selecionados.filter((i) => i !== item);
    } else {
      if (this.selecionados.length >= 2) {
        alert('Selecione no máximo 2 registros');
        return;
      }
      this.selecionados.push(item);
    }
  }

  isDisabled(item: any): boolean {
    return this.selecionados.length >= 2 && !this.selecionados.includes(item);
  }

  origem(): any {
    return this.selecionados[0];
  }

  destino(): any {
    return this.selecionados[1];
  }

  isHabilitaTransf(){
    return this.selecionados.length >= 2 && this.transferncia.amount > 0;
  }

  editar(id: number) {
    this.edicao = true;
    this.idEdicao = id;
    this.beneficioService.getBeneficioById(id).subscribe((data) => {
      this.form.patchValue({
        nome: data.nome,
        descricao: data.descricao,
        valor: data.valor,
        ativo: data.ativo,
      });
    });
  }

  excluir(id: number) {
    if (confirm('Deseja realmente excluir?')) {
      this.beneficioService.delBeneficio(id).subscribe({
        next: (result) => {
          alert('Excluido com sucesso');
          this.loadBeneficio();
          this.form.reset();
          this.edicao = false;
          this.idEdicao = 0;
          this.selecionados = [];
          this.cdr.markForCheck();
        },
        error: (err) => {
          alert('Erro ao excluir');
        },
      });
    }
  }

  cancelarEdicao() {
    this.edicao = false;
    this.idEdicao = 0;
    this.form.reset();
  }

  submit() {
    if (this.form.invalid) return;
    const payload = this.form.value;

    if (this.edicao) {
      this.beneficioService.update(this.idEdicao, payload).subscribe({
        next: (result) => {
          alert('Atualizado com sucesso');
          this.loadBeneficio();
          this.form.reset();
          this.edicao = false;
          this.idEdicao = 0;
          this.cdr.markForCheck();
        },
        error: (err) => {
          alert('Erro ao atualizar');
        },
      });
    } else {
      this.beneficioService.create(payload).subscribe({
        next: (result) => {
          alert('Criado com sucesso');
          this.loadBeneficio();
          this.form.reset();
          this.cdr.markForCheck();
        },
        error: (err) => {
          alert('Erro ao criar');
        },
      });
    }
  }

  onValorInput(event: any) {
    const input = event.target;

    const formatted = this.formatCurrency(input.value);
    input.value = formatted;
    const numeric = this.parseCurrency(formatted);

    this.form.patchValue({ valor: numeric }, { emitEvent: false });
  }

  parseCurrency(value: string): number {
    if (!value) return 0;

    return Number(
      value
        .replace(/\./g, '')
        .replace(',', '.'),
    );
  }

  formatCurrency(value: string): string {
    if (!value) return '';
    let numeric = value.replace(/\D/g, '');
    if (!numeric) return '';
    const number = Number(numeric) / 100;
    return number.toLocaleString('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    });
  }

  concluirTransferencia() {

    this.transferncia.fromId = this.origem().id;
    this.transferncia.toId = this.destino().id;

    this.beneficioService.transfer(this.transferncia).subscribe({
      next: (result) => {
        alert('Transferencia realizada com sucesso');
        this.loadBeneficio();
        this.form.reset();
        this.edicao = false;
        this.idEdicao = 0;
        this.transferncia = new TransferRequestDTO();
        this.cdr.markForCheck();
      },
      error: (err) => {
        alert('Erro ao transferir');
      },
    });

  }
}
