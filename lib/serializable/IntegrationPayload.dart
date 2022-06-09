import 'dart:core';

import 'SourceData.dart';
import 'TargetData.dart';

class IntegrationPayload {

  final String source;
  final String target;
  final SourceData sourceData;
  final TargetData targetData;

  IntegrationPayload(this.source, this.target, this.sourceData, this.targetData);

  IntegrationPayload.fromJson(Map<String, dynamic> json)
    : source = json['source'],
      target = json['target'],
      sourceData = json['source_data'],
      targetData = json['target_data']
  ;

  Map<String, dynamic> toJson() => {
    'source' : source,
    'target': target,
    'source_data': sourceData,
    'target_data': targetData
  };

}