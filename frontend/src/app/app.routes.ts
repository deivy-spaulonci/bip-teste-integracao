import { Routes } from '@angular/router';
import {BenificioView} from './view/benificio-view/benificio-view';
import {HomeView} from './view/home-view/home-view';

export const routes: Routes = [
  {path:'beneficio', component: BenificioView},
  {path:'home', component: HomeView},
  { path: '',   redirectTo: '/home', pathMatch: 'full' },
];
