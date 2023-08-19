import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, first } from 'rxjs';
import { Badge } from 'src/app/models/Badge';
import { environment } from 'src/environments/environment';
import { saveAs } from 'file-saver';

@Injectable({
  providedIn: 'root',
})
export class HttpService {
  constructor(private http: HttpClient) {}

  getAllBadges(): Promise<Badge[]> {
    return new Promise<Badge[]>((resolve, reject) => {
      let host = `${environment.API_URL}/api/v1/Badge`;
      this.http
        .get<Badge[]>(host)
        .pipe(first())
        .subscribe({
          next: (d: Badge[] | PromiseLike<Badge[]>) => resolve(d),
          error: (_: any) => reject(),
        });
    });
  }

  deleteBadgeById(id: number) {
    return new Promise<Boolean>((resolve, reject) => {
      this.http
        .delete(`${environment.API_URL}/api/v1/Badge/${id}`)
        .pipe(first())
        .subscribe({
          next: (d) => resolve(true),
          error: (e) => reject(false),
        });
    });
  }

  createBadge(formData: FormData) {
    return new Promise((resolve, reject) => {
      this.http
        .post(`${environment.API_URL}/api/v1/Badge`, formData)
        .subscribe({
          next: (data: any) => {
            if (data.success) {
              const byteString = atob(data.imageData);
              const arrayBuffer = new ArrayBuffer(byteString.length);
              const uint8Array = new Uint8Array(arrayBuffer);

              for (let i = 0; i < byteString.length; i++) {
                uint8Array[i] = byteString.charCodeAt(i);
              }
              
              const blob = new Blob([uint8Array], { type: 'image/png' });

              // Save the Blob as a downloadable file
              saveAs(blob, 'badge_image.png');
              resolve(data.imageData);
            } else {
              reject(data.errorMessage);
            }
          },
        });
    });
  }
}
