class TargetDataItem {

  final String name;
  final String type;
  final String value;

  TargetDataItem(this.name, this.type, this.value);

  TargetDataItem.fromJson(Map<String, dynamic> json)
      : name = json['name'],
        type = json['type'],
        value = json['value']
  ;

  Map<String, dynamic> toJson() => {
    'name' : name,
    'type': type,
    'value': value
  };

}