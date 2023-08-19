import { Component, Input, OnInit, AfterViewInit } from '@angular/core';

@Component({
  selector: 'app-alert',
  templateUrl: './alert.component.html',
  styleUrls: ['./alert.component.css'],
})
export class AlertComponent implements OnInit, AfterViewInit {
  @Input() data: AlertData | undefined;
  tcolor: string = 'black';

  constructor() { }
  
  ngAfterViewInit(): void {
    if (this.data) {
      const type = this.data.type?.toLowerCase();
      switch (type) {
        case 'success':
          this.tcolor = '#4cbb17';
          break;
        case 'error':
          this.tcolor = 'red';
          break;
        case 'loading':
        case 'info':
          this.tcolor = 'blue';
          break;
      }
    }
  }

  ngOnInit(): void {
    
  }
}

export type AlertData = {
  type: string | undefined;
  message: string | undefined;
};
