import { Component } from '@angular/core';
import { HttpService } from '../../services/http.service';
import { Badge } from 'src/app/models/Badge';
import { AlertData } from 'src/app/shared/components/alert/alert.component';

@Component({
  selector: 'app-liste-badge',
  templateUrl: './liste-badge.component.html',
  styleUrls: ['./liste-badge.component.css'],
})
export class ListeBadgeComponent {
  badges: Badge[] = [];
  public isFetchingItems: boolean = true;
  alertData: AlertData | undefined = {
    message: "S'il vous plaît, attendez",
    type: 'loading',
  };
  constructor(private httpService: HttpService) {}

  ngOnInit(): void {
    this.alertData = {
      type: 'loading',
      message: 'loading',
    };
    this.httpService
      .getAllBadges()
      .then((data) => {
        this.isFetchingItems = false;
        this.badges = data;
        console.log(data);
        this.alertData = {
          type: 'success',
          message: 'success',
        };
      })
      .catch(() => {
        this.alertData = {
          message: 'Erreur lors de la récupération des données',
          type: 'error',
        };
      })
      .finally(() => setTimeout(() => (this.alertData = undefined), 3000));
  }

  deleteBadgeById(badge: Badge) {
    this.isFetchingItems = true;
    this.alertData = {
      type: 'loading',
      message: 'loading',
    };
    this.httpService
      .deleteBadgeById(badge.id)
      .then((data) => {
        if (data) {
          const index = this.badges.indexOf(badge);
          this.badges.splice(index, 1);
        }
        console.log(data);
        this.alertData = {
          type: 'success',
          message: 'supprimé avec succès',
        };
      })
      .catch(() => {
        this.alertData = {
          message: 'Erreur lors de la suppression',
          type: 'error',
        };
      })
      .finally(() => {
        setTimeout(() => (this.alertData = undefined), 3000);
        this.isFetchingItems = false;
      });
  }
}
