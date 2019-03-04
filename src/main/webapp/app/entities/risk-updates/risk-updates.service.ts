import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRiskUpdates } from 'app/shared/model/risk-updates.model';

type EntityResponseType = HttpResponse<IRiskUpdates>;
type EntityArrayResponseType = HttpResponse<IRiskUpdates[]>;

@Injectable({ providedIn: 'root' })
export class RiskUpdatesService {
    public resourceUrl = SERVER_API_URL + 'api/risk-updates';

    constructor(protected http: HttpClient) {}

    create(riskUpdates: IRiskUpdates): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(riskUpdates);
        return this.http
            .post<IRiskUpdates>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(riskUpdates: IRiskUpdates): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(riskUpdates);
        return this.http
            .put<IRiskUpdates>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRiskUpdates>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRiskUpdates[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(riskUpdates: IRiskUpdates): IRiskUpdates {
        const copy: IRiskUpdates = Object.assign({}, riskUpdates, {
            updatedAt: riskUpdates.updatedAt != null && riskUpdates.updatedAt.isValid() ? riskUpdates.updatedAt.format(DATE_FORMAT) : null
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
            res.body.forEach((riskUpdates: IRiskUpdates) => {
                riskUpdates.updatedAt = riskUpdates.updatedAt != null ? moment(riskUpdates.updatedAt) : null;
            });
        }
        return res;
    }
}
