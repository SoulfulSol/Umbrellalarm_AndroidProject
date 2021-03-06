

#### **기획의도**

Umbrellalarm, 우산알라미

매일 기상하고 아침에 정신없이 준비하는 직장인들은 우천시에도 우산을 잊어버리고 안 가져가는 일이 비일비재합니다.

저 또한 늦잠형 인간인 탓에, 매일 아침 오늘의 날씨를 확인하는 일을 잊어버립니다.

그로인해 우산을 깜박하고 가져가지 않아서,

퇴근길에 비가 내리면 아까운 돈을 내고 다음 번에 쓰지도 않을 것 같은 일회용 우산을 사곤 했습니다.

<br>

**"매일 아침, 우산을 가져가야 할 지 말 지 판단을 도와주는 Application을 만들면 어떨까?"**

<br>

#### 구현에 활용한 기술

- Andoroid Studio
- SQLite

<br>

#### 앱 기능 구상

- 로딩화면 이후 메인 액티비티로 진입한다.
- 아무 설정도 되어있지 않으면 알람 설정 추가 버튼을 눌러 프래그먼트 창을 띄운다.
- 프래그먼트에서 희망 알람 시간, 지역 정보 등을 설정한다.
- 설정이 끝나면 설정 정보를 DB에 저장하고, AlarmManager를 설정한다.
- 설정 정보를 메인화면에 표시한다.
- 설정한 알람 시간에 설정 시간의 강수확률이 푸시알람 형태로 표시된다.

<br>

#### 프로젝트 진행 시 겪었던 문제들

- 프로젝트 당시 객체 지향 방법론에 대해 이해가 거의 없었기 때문에 클래스를 여러 개 만들지 않았고, 그저 화면 단위로만 클래스를 생성하였다.
- 그 결과 추후 구현 내용 변경시 여기저기 예상치 못했던 부분도 수정했어야만 했다.
- 강수확률 API를 받아와 데이터를 넘겨줄 때 **API 호출 및 실의 시간이 다소 ** 감소한다.

<br>

#### 문제 해결의 방법들

첫 개발부터 '객체지향 설계'를 염두에 두고 개발을 하진 않았다.

OOP 관련 책도 읽어 보았으나, 아직 1회독을 한 것은 아니다.



<br>

#### 배운점 및 아쉬웠던 점

