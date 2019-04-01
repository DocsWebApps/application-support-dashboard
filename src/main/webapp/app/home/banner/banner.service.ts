import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { createRequestOption } from 'app/shared';
import { IIncident } from 'app/shared/model/incident.model';
import { SERVER_API_URL } from 'app/app.constants';
import { Stats } from 'app/shared/model/stats.model';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class BannerService {
    private incidentResourceUrl = SERVER_API_URL + 'api/incidents/incident';
    private statsResourceUrl = SERVER_API_URL + 'api/incidents/stats';

    constructor(private http: HttpClient) {}

    getBannerIncident(req?: any): Observable<HttpResponse<IIncident>> {
        const options = createRequestOption(req);
        return this.http.get<IIncident>(this.incidentResourceUrl, { params: options, observe: 'response' }).pipe(
            map(res => {
                const body: IIncident = res.body;
                return res.clone({ body });
            })
        );
    }

    getBannerStats(req?: any): Observable<HttpResponse<Stats>> {
        const options = createRequestOption(req);
        return this.http.get<Stats>(this.statsResourceUrl, { params: options, observe: 'response' }).pipe(
            map(res => {
                const body: Stats = res.body;
                return res.clone({ body });
            })
        );
    }
}
