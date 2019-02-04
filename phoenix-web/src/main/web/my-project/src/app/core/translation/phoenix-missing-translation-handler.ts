import {MissingTranslationHandler, MissingTranslationHandlerParams} from '@ngx-translate/core';

export class PhoenixMissingTranslationHandler implements MissingTranslationHandler {
  handle(params: MissingTranslationHandlerParams) {
    return "Resource not resolved:{{'" + params.key + "' | translate}}";
  }
}
