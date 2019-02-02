import {TranslateLoader, TranslateService} from '@ngx-translate/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {Resource} from "../../models/resource";
import {Observable} from "rxjs";
import * as _ from "lodash";

export class HttpResourceLoader implements TranslateLoader {

  constructor(private http: HttpClient, private translateService: TranslateService) {}

  getTranslation(lang: string): Observable<any> {
    return this.http.get("/resources?lang=" + lang).pipe(map((response: Resource[]) => {
      var data = _.filter(response, function(o) { return o.locale === lang});

      return _.mapValues(_.keyBy(data, 'code'), v => v.text);
    }));
  }


}
