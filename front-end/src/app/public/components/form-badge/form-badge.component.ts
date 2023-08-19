import { Component } from '@angular/core';
import { Badge } from 'src/app/models/Badge';
import { AlertData } from 'src/app/shared/components/alert/alert.component';
import { HttpService } from '../../services/http.service';
import { FormArray, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-form-badge',
  templateUrl: './form-badge.component.html',
  styleUrls: ['./form-badge.component.css'],
})
export class FormBadgeComponent {
  badges: Badge[] = [];
  zone_acces: String[] = [];
  selectedFile: File | null = null;
  public isFetchingItems:boolean = false;
  alertData: AlertData | undefined ;

  form = new FormGroup({
    Nom: new FormControl('', [Validators.required]),
    Prenom: new FormControl('', [Validators.required]),
    // Cnie: new FormControl('', [Validators.required, Validators.minLength(8), Validators.maxLength(8)]),
    Cnie: new FormControl('', [Validators.required]),
    Organisme: new FormControl('', [Validators.required]),
    Fonction: new FormControl('', [Validators.required]),
    status: new FormControl('', [Validators.required]),
    date_depot_dossier: new FormControl('', [Validators.required]),
    date_envoi_dai: new FormControl('', [Validators.required]),
    date_retour_dai: new FormControl('', [Validators.required]),
    zone_acces1: new FormControl(''),
    zone_acces2: new FormControl(''),
    zone_acces3: new FormControl(''),
  });

  constructor(private httpService: HttpService) {}

  onSubmit() {
    this.isFetchingItems = true
    const zone_acces1Control = this.form.get('zone_acces1');
    const zone_acces2Control = this.form.get('zone_acces2');
    const zone_acces3Control = this.form.get('zone_acces3');

    if (zone_acces1Control && zone_acces1Control.value) {
      this.zone_acces.push('green');
    }
    if (zone_acces2Control && zone_acces2Control.value) {
      this.zone_acces.push('blue');
    }
    if (zone_acces3Control && zone_acces3Control.value) {
      this.zone_acces.push('chestnut');
    }
    const formData = new FormData();
   
    if (this.selectedFile && this.form.valid) {
      
      formData.append('nom', this.form.controls['Nom'].value ?? '');
      formData.append('prenom', this.form.controls['Prenom'].value ?? '');
      formData.append('organisme', this.form.controls['Organisme'].value ?? '');
      formData.append('cnie', this.form.controls['Cnie'].value ?? '');
      formData.append('fonction', this.form.controls['Fonction'].value ?? '');
      formData.append('date_depot_dossier', this.form.controls['date_depot_dossier'].value ?? '');
      formData.append('date_envoi_dai', this.form.controls['date_envoi_dai'].value ?? '');
      formData.append('date_retour_dai', this.form.controls['date_retour_dai'].value ?? '');
      formData.append('zone_acces', this.zone_acces.join(','));
      formData.append('status', this.form.controls['status'].value ?? '');
      formData.append('image', this.selectedFile, this.selectedFile.name);
    }
    this.alertData = {
      type: 'loading',
      message: 'loading',
    };
    this.httpService.createBadge(formData).then(data => {
      
      console.log(data)
      this.alertData = {
        type: 'success',
        message: "succès",
      }
    }).catch((err) => {
      
      this.alertData = { message: err, type: "error" }
    }).finally(() => {setTimeout(() => this.alertData = undefined, 3000);
      this.isFetchingItems = false});
    
    this.zone_acces = [];
  }

  onFileSelected(event: any) {
    this.selectedFile = event.target.files[0];
  }
}
