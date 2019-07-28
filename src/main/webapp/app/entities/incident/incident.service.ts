import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIncident } from 'app/shared/model/incident.model';

type EntityResponseType = HttpResponse<IIncident>;
type EntityArrayResponseType = HttpResponse<IIncident[]>;

@Injectable({ providedIn: 'root' })
export class IncidentService {
    private _selectedStatus = 'ALL';
    private _selectedSeverity = 'ALL';
    public resourceUrl = SERVER_API_URL + 'api/incidents';

    constructor(protected http: HttpClient) {}

    get selectedStatus(): string {
        return this._selectedStatus;
    }

    set selectedStatus(value: string) {
        this._selectedStatus = value;
    }

    get selectedSeverity(): string {
        return this._selectedSeverity;
    }

    set selectedSeverity(value: string) {
        this._selectedSeverity = value;
    }

    create(incident: IIncident): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(incident);
        return this.http
            .post<IIncident>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(incident: IIncident): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(incident);
        return this.http
            .put<IIncident>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIncident>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(status: string, severity: string, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIncident[]>(`${this.resourceUrl}/${status}/${severity}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    problemIncidents(id: number, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IIncident[]>(`${this.resourceUrl}/problem/${id}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}/delete`, { observe: 'response' });
    }

    close(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}/close`, { observe: 'response' });
    }

    protected convertDateFromClient(incident: IIncident): IIncident {
        const copy: IIncident = Object.assign({}, incident, {
            openedAt: incident.openedAt != null && incident.openedAt.isValid() ? incident.openedAt.format(DATE_FORMAT) : null,
            closedAt: incident.closedAt != null && incident.closedAt.isValid() ? incident.closedAt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.openedAt = res.body.openedAt != null ? moment(res.body.openedAt) : null;
            res.body.closedAt = res.body.closedAt != null ? moment(res.body.closedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((incident: IIncident) => {
                incident.openedAt = incident.openedAt != null ? moment(incident.openedAt) : null;
                incident.closedAt = incident.closedAt != null ? moment(incident.closedAt) : null;
            });
        }
        return res;
    }
}
