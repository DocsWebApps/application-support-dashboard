import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRisk } from 'app/shared/model/risk.model';

type EntityResponseType = HttpResponse<IRisk>;
type EntityArrayResponseType = HttpResponse<IRisk[]>;

@Injectable({ providedIn: 'root' })
export class RiskService {
    public resourceUrl = SERVER_API_URL + 'api/risks';

    constructor(protected http: HttpClient) {}

    create(risk: IRisk): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(risk);
        return this.http
            .post<IRisk>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(risk: IRisk): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(risk);
        return this.http
            .put<IRisk>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRisk>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRisk[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(risk: IRisk): IRisk {
        const copy: IRisk = Object.assign({}, risk, {
            openedAt: risk.openedAt != null && risk.openedAt.isValid() ? risk.openedAt.format(DATE_FORMAT) : null,
            closedAt: risk.closedAt != null && risk.closedAt.isValid() ? risk.closedAt.format(DATE_FORMAT) : null
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
            res.body.forEach((risk: IRisk) => {
                risk.openedAt = risk.openedAt != null ? moment(risk.openedAt) : null;
                risk.closedAt = risk.closedAt != null ? moment(risk.closedAt) : null;
            });
        }
        return res;
    }
}
