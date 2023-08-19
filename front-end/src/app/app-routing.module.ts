import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContainerComponent } from './public/components/container/container.component';

const routes: Routes = [
  { path: '', redirectTo: 'home/addBadge', pathMatch: 'full' },
  { path: 'home', redirectTo: 'home/addBadge', pathMatch: 'full' },
  { path: 'home',
    loadChildren: () => import('./public/public.module').then(x => x.PublicModule),
   component: ContainerComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
