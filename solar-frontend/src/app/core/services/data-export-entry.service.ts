import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Dataexportentry } from '../models/dataexportentry';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DataExportEntryService {

  private dataexportentriesUrl: string;

  constructor(private http: HttpClient) {
    this.dataexportentriesUrl = 'http://localhost:8080/getDataExportEntryByIID';
  }
   public findByIid(iid: string | null | undefined): Observable<Dataexportentry> {
      return this.http.get<Dataexportentry>(this.dataexportentriesUrl + '?iid=' + iid);
    }

}
