# Devops

![Untitled](./assets/devops.png)

- Development + Operations의 합성어
  - 소프트웨어 개발자와 정보기술 전문가 간의 소통, 협업 및 통합을 강조하는 개발 환경이나 문화
  - 소프트웨어 제품이나 서비스를 알맞은 시기에 출시하기 위해 개발과 운영이 상호 의존적으로 대응해야 한다는 의미로 많이 사용
  - 데브옵스의 개념은 애자일 기법과 지속적 통합의 개념과도 관련이 있다.
    - 애자일 기법
      작업 계획을 짧은 단위로 세우고 제품을 만들고 고쳐 나가는 사이클을 반복함으로써 고객의 요구변화에 **유연 & 신속하게 대응**하는 개발 방법론이다.
    - 지속적 통합
      통합 작업을 초기부터 계속 수행해서 지속적으로 소프트웨어의 품질 제어를 적용하는 것

## **DevOps의 특징들**

**Cross Functional Team**

하나의 팀에 개발 부터 운영까지 모두 할 수 있는 사람들로 채우라는뜻이 아니라, 각 프로세스의(**개발 ~ 배포 및 테스트까지**) 담당자들을 하나의 팀으로 모으라는 뜻이다. 서비스 기획부터 개발 운영 테스트 배포등 모든 제품 개발 프로세스를 하나의 팀에서 할 수 있도록 해야 한다는것이 Cross Functional Team이다.

**Widely Shared Metrics**

한마디로, 팀원 모두가 알고있는 하나의 공유된 지표가 필요하다는것이다.

서비스를 개발만 하는게 아니라 서비스가 운영에서 잘 돌아가고 있는지, 사용자의 반응은 어떤지를 측정할 수 있는 기준이 필요하다는것이다. 그리고 이 지표를 기준으로 팀원들이 인지할 수 있도록 해야한다.

**Automating repetitive tasks**

반복적인 일들은 자동화 하라는것이다. CI/CD를 이용해서 빌드-배포-테스트 프로세스를 자동화 해야한다. 반복작업에 투입되는 시간을 줄여야 좀 더 생산적으로 일할수 있고 좀 더 고도화된 서비스를 만들 여유와 시간을 벌 수 있을것이다. 고급인력들을 데려다 놓고 반복작업에 시간을 쏟게 하는것은 개인적으로나 회사 전체로보나 손해이다. **그리고 자동화 툴을 만드는 과정에서 시스템 전체에 대한 이해가 높아진다. 여러모로 장점이 많다.**

**Post Mortems**

직역하자면 후처리라고 할 수 있다. 장애나 이슈가 있을때 그걸 혼자만 알지 말고 팀원들과 공유를 해야한다. 서비스를 운영만 하다보면 어떤 이슈가 있을때 이 이슈가 얼마나 큰 이슈인지를 파악하지 못할떄가 많다.

**Regular Release**

짧은 주기의 정기 배포를 통해서 빠르게 서비스의 기능을 개선하고 고객들의 VoC를 반영해 나가야한다.
