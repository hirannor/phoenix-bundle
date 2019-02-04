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
      var filteredTranslations = _.filter(response, function(o) { return o.locale === lang});
      var array = _.map(filteredTranslations, function(o) {
        var replacedText =_.replace(o.text, /#/g, '').replace(/{/g, '{{').replace(/}/g, '}}');
        return _.assign(o, {
          text: replacedText
        });
      });
      return _.mapValues(_.keyBy(array, 'code'), v => v.text);
    }));
  }

}
