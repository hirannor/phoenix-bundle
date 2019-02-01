import {MissingTranslationHandler, MissingTranslationHandlerParams} from '@ngx-translate/core';

export class PhoenixMissingTranslationHandler implements MissingTranslationHandler {
  handle(params: MissingTranslationHandlerParams) {
    return "{{'" + params.key + "' | translate}}";
  }
}
