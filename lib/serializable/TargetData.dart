import 'TargetDataItem.dart';

class TargetData {

  final List<TargetDataItem> targetDataList;

  TargetData(this.targetDataList);

  void addDataItem(name, type, value) {
    targetDataList.add(TargetDataItem(name, type, value));
  }

}