import { SERVER_API_URL } from 'app/app.constants';
import { EventEmitter, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AppStatus } from 'app/shared/model/status.model';
import { createRequestOption } from 'app/shared';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class StatusService {
    private resourceUrl = SERVER_API_URL + 'api/apps/status';
    incidentSwitch = new EventEmitter<string>();

    constructor(private http: HttpClient) {}

    getAppStatus(req?: any): Observable<HttpResponse<AppStatus>> {
        const options = createRequestOption(req);
        return this.http.get<AppStatus>(this.resourceUrl, { params: options, observe: 'response' }).pipe(
            map(res => {
                const body: AppStatus = res.body;
                return res.clone({ body });
            })
        );
    }
}
