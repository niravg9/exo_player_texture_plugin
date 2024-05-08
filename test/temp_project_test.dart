import 'package:flutter_test/flutter_test.dart';
import 'package:temp_project/temp_project.dart';
import 'package:temp_project/temp_project_platform_interface.dart';
import 'package:temp_project/temp_project_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockTempProjectPlatform
    with MockPlatformInterfaceMixin
    implements TempProjectPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final TempProjectPlatform initialPlatform = TempProjectPlatform.instance;

  test('$MethodChannelTempProject is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelTempProject>());
  });

  test('getPlatformVersion', () async {
    TempProject tempProjectPlugin = TempProject();
    MockTempProjectPlatform fakePlatform = MockTempProjectPlatform();
    TempProjectPlatform.instance = fakePlatform;

    expect(await tempProjectPlugin.getPlatformVersion(), '42');
  });
}
