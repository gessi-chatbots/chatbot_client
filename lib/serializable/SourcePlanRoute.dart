import 'package:mobile_catalogue_chatbot/serializable/SourceData.dart';

class SourcePlanRoute extends SourceData {

  final String lat;
  final String long;

  SourcePlanRoute(this.lat, this.long);

  SourcePlanRoute.fromJson(Map<String, dynamic> json)
      : lat = json['lat'],
        long = json['long'];

  Map<String, dynamic> toJson() => {
    'lat' : lat,
    'long': long
  };

}