import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContainerComponent } from './components/container/container.component';
import { FormBadgeComponent } from './components/form-badge/form-badge.component';
import { ListeBadgeComponent } from './components/liste-badge/liste-badge.component';

const routes: Routes = [
  { path: 'home', redirectTo: 'home/addBadge', pathMatch: 'full' },

  {
    path: 'home',
    component: ContainerComponent,
    children: [
      { path: 'addBadge', component: FormBadgeComponent },
      { path: 'ListBadge', component: ListeBadgeComponent },
      
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PublicRoutingModule { }
