# 🏠 숙박 예약 서비스 야널자 Backend

야놀자 백엔드 클론 프로젝트

개발기간 : 2024-01-08 ~ 2024-02-02

API 스웨거 링크 : https://api.yanullja.com
<br>
<br>

## 📖 프로젝트 소개

야놀자 숙소 예약 서비스의 백엔드 클론 프로젝트입니다.

조건에 맞게 필터를 설정하여 자신에게 필요한 숙소를 검색하고 찾아볼 수 있습니다.

숙소를 예약하고 후기를 작성하여 평점을 남길 수 있습니다.


<br>

## 💡 기술적 도전

### 김대현

### 김민우

|문제|1대N 관계에 있는 테이블 간에 '1'을 기준으로 페이징 처리를 해야 하고, 'N'을 기준으로 조건 필터링이 필요한 상황.|
|:---|:------------------------------------------------------------------------------------------------------------|
|해결|'1'테이블과 'N'테이블을 JOIN하고, 'N'을 기준으로 조건 필터링 후 distinct 처리를 통해 '1'의 데이터만을 조회. <br> JPA의 Lazy Loading으로 '다'에 해당하는 데이터를 가져오도록 애플리케이션단에서 처리. <br> ( N + 1 문제가 발생하지 않도록 Batch size 설정 ) |

<br>

|문제 |QueryDsl에서 동적으로 JOIN을 적용해야 하는 상황|
|:----|:---------------------------------------------|
|해결1|여러개의 메소드를 만들어서 상황에 따라 맞는 메소드를 호출하도록 구현.|
|해결2|조건에 따라 동적으로 JOIN을 적용하는 메소드를 만들어 적용. <br> 가독성을 증진시키기 위해 들여쓰기를 활용하여 기존의 JOIN 형태와 비슷하게 유지.|

__해결2 코드__
```.java
List<Review> r;

JPAQuery<Review> selectQuery = query
    .select(review)
    .distinct()
    .from(review)
    .join(review.member, member).fetchJoin()
    .join(review.room, room).fetchJoin();
r = innerJoinIfPhotoOnly(selectQuery, cond.getHasPhoto())
    .where(
        review.place.id.eq(cond.getPlaceId()),
        roomIdEq(cond.getRoomId())
    )
    .orderBy(reviewSort(cond))
    .offset(pageable.getOffset())
    .limit(pageable.getPageSize() + 1)
    .fetch();
```


### 이비안

### 임현우 

### 염금성


좀 더 자세한 내용은 노션을 참고바랍니다. <br>
( 링크 : https://www.notion.so/bc8f4c65b042459bb22736d25da181dc )

<br>


## Architecture

<br>

## ERD
<img src="https://github.com/battlecruisers/yanullja/assets/106314016/0b84bbce-fd0e-4a41-8881-4f64086bbbdd" />

<br>
<br>

## ⚙ 기술 스택
- Backend: `Java 17` `Spring Boot 3.2.1` `Spring Security` `Spring Data JPA` `QueryDsl`
- Frontend: `React` `TypeScript`
- DB: `PostgreSQL` `Redis`
- Server: `AWS EC2`
- Tools: `Intellij IDEA`
- Collaborations: `Github Projects`
<br>

## 협업 전략

- Squash Merge Pull Request만을 사용한다.
- GitHub flow 전략을 사용한다.
- 한 사람 이상의 Accepted Review가 있어야만 PR을 머지할 수 있다.

<br>

## 회의록
링크 : https://www.notion.so/0956e741633e4862bd355ac2eadbd2ad

<br>

## 백엔드 DEMO

> [!IMPORTANT]
> 현재 프로젝트는 백엔드 api만을 작성한 상태이며, 프론트 코드는 완성되지 않은 상태입니다.
> 하단에 보이는 데모 스크린샷은
>
> https://github.com/Yanolja-MiniProject-10/Yanolja-clone-fe
>
> 위 프로젝트의 프론트 코드를 그대로 가져와서 로컬에 돌려서 api endpoint를 맞춘 후 동작을 확인한 내용입니다.
>
> 저희 팀원 5명은 **프론트적인 역량이 없다**는 것을 명시합니다!
### 로그인

<img src="https://github.com/battlecruisers/yanullja/assets/106314016/ae5e8a7e-4370-4fc2-9da9-26247a64c961" />
<br><br>


### 숙소 조회

<img src="https://github.com/battlecruisers/yanullja/assets/106314016/d871d361-c917-43db-8964-e5caf2b812f1" />
<br><br>

### 예약

<img src="https://github.com/battlecruisers/yanullja/assets/106314016/b5cba42a-ffc2-463d-97c7-c9903447118a" />
<br><br>

### 리뷰 조회

<img src="https://github.com/battlecruisers/yanullja/assets/106314016/b7c2b7e6-6f2f-49fa-b9e4-ca4b06e68f4d" />

<br>
<br>
<br>

## 👊 팀 Battlecrusiers

<table align=center>
    <thead>
        <tr>
            <th style="text-align:center;" >김대현</th>
            <th style="text-align:center;" >김민우</th>
            <th style="text-align:center;" >염금성</th>
            <th style="text-align:center;" >이비안</th>
            <th style="text-align:center;" >임현우</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><img width="200" src="https://avatars.githubusercontent.com/u/18080546?v=4" /> </td>
            <td><img width="200" src="https://avatars.githubusercontent.com/u/106314016?s=400&u=45da4393440e6b034aca9b440fd717de3978ce7f&v=4" /></td>
            <td><img width="200" src="https://avatars.githubusercontent.com/u/102720261?s=400&u=37f1cfc2098c624e58f0693d4d593c17bb5080fc&v=4" /> </td>
            <td><img width="200" src="https://avatars.githubusercontent.com/u/87007010?v=4" /> </td>
            <td><img width="200" src="https://avatars.githubusercontent.com/u/97041290?v=4" /> </td>
        </tr>
        <tr>
            <td style="text-align:center;"><a href="https://github.com/vimkim">@vimkim</a></td>
            <td style="text-align:center;"><a href="https://github.com/yukicow">@yukicow</a></td>
            <td style="text-align:center;"><a href="https://github.com/Venus1234567">@Venus1234567</a></td>
            <td style="text-align:center;"><a href="https://github.com/gumgu">@gumgu</a></td>
            <td style="text-align:center;"><a href="https://github.com/hyunwoo0318">@hyunwoo0318</a></td>
        </tr>
        <tr>
            <td width="200">건강한 신체에 건강한 코드가 깃든다. 모두가 행복한 체덕지 프로그래밍 화이팅!</td>
            <td width="200">꾸준하게 공부해서 끝없이 성장하자.</td>
            <td width="200">많은 문제에 부딫혀가며 꾸준히 성장하는 개발자 되기</td>
            <td width="200">믿을 수 있는 동료 개발자가 되겠습니다! 화이팅!!</td>
            <td width="200">항상 꼼꼼하고 행복하게 코딩하자~</td>
        </tr>
    </tbody>
</table>
<br>
