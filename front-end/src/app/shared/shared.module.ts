import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { AlertComponent } from './components/alert/alert.component';
import { SharedRoutingModule } from './shared-routing.module';

@NgModule({
  declarations: [
    PageNotFoundComponent,
    AlertComponent,
 
  ],
  imports: [CommonModule,SharedRoutingModule],
  exports: [PageNotFoundComponent,AlertComponent,SharedRoutingModule,],
})
export class SharedModule {}
