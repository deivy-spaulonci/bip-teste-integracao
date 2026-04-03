import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { DefaultService } from '../../service/default.service';
import { BenificioService } from '../../service/benificio-service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Beneficio } from '../../model/beneficio';

@Component({
  selector: 'app-benificio-view',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './benificio-view.html',
  styleUrl: './benificio-view.css',
})
export class BenificioView implements OnInit {
  beneficioResp$!: Observable<any>;
  form!: FormGroup;
  edicao: boolean = false;
  idEdicao: number = 0;

  constructor(
    private beneficioService: BenificioService,
    private cdr: ChangeDetectorRef,
    private fb: FormBuilder,
  ) {}

  ngOnInit(): void {
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

    // atualiza visualmente
    input.value = formatted;

    // opcional: salvar valor numérico limpo no form
    const numeric = this.parseCurrency(formatted);

    this.form.patchValue({ valor: numeric }, { emitEvent: false });
  }

  parseCurrency(value: string): number {
    if (!value) return 0;

    return Number(
      value
        .replace(/\./g, '') // remove milhar
        .replace(',', '.'), // troca decimal
    );
  }

  formatCurrency(value: string): string {
    if (!value) return '';

    // remove tudo que não for número
    let numeric = value.replace(/\D/g, '');

    // evita vazio
    if (!numeric) return '';

    // converte para número (centavos)
    const number = Number(numeric) / 100;

    // formata padrão BR
    return number.toLocaleString('pt-BR', {
      minimumFractionDigits: 2,
      maximumFractionDigits: 2,
    });
  }
}
