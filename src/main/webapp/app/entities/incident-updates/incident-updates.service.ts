import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IIncidentUpdates } from 'app/shared/model/incident-updates.model';

type EntityResponseType = HttpResponse<IIncidentUpdates>;
type EntityArrayResponseType = HttpResponse<IIncidentUpdates[]>;

@Injectable({ providedIn: 'root' })
export class IncidentUpdatesService {
    public resourceUrl = SERVER_API_URL + 'api/incident-updates';
    private _incidentID;
    private _returnRoute;

    constructor(protected http: HttpClient) {}

    get returnRoute() {
        return this._returnRoute;
    }

    set returnRoute(value) {
        this._returnRoute = value;
    }

    get incidentID() {
        return this._incidentID;
    }

    set incidentID(value) {
        this._incidentID = value;
    }

    create(incidentUpdates: IIncidentUpdates): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(incidentUpdates);
        return this.http
            .post<IIncidentUpdates>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(incidentUpdates: IIncidentUpdates): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(incidentUpdates);
        return this.http
            .put<IIncidentUpdates>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IIncidentUpdates>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(id: number, req?: any): Observable<HttpResponse<IIncidentUpdates[]>> {
        const options = createRequestOption(req);
        return this.http
            .get<IIncidentUpdates[]>(`${this.resourceUrl}/incident/${id}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(incidentUpdates: IIncidentUpdates): IIncidentUpdates {
        const copy: IIncidentUpdates = Object.assign({}, incidentUpdates, {
            updatedAt:
                incidentUpdates.updatedAt != null && incidentUpdates.updatedAt.isValid()
                    ? incidentUpdates.updatedAt.format(DATE_FORMAT)
                    : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((incidentUpdates: IIncidentUpdates) => {
                incidentUpdates.updatedAt = incidentUpdates.updatedAt != null ? moment(incidentUpdates.updatedAt) : null;
            });
        }
        return res;
    }
}
