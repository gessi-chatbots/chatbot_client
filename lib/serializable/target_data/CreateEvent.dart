import 'package:mobile_catalogue_chatbot/serializable/TargetData.dart';

class CreateEvent extends TargetData {

  CreateEvent(startDateTime, endDateTime, invites, name, description) : super([]) {
    addDataItem("start date/time", "Calendar", startDateTime);
    addDataItem("end date/time", "Calendar", endDateTime);
    addDataItem("invites", "SetOf::EMail", invites);
    addDataItem("name", "Text", name);
    addDataItem("description", "Text", description);
  }

}