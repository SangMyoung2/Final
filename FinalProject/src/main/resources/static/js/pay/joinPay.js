function joinGroup() {
    // 아래의 코드는 axios를 사용하여 API를 호출하는 예입니다.
    // 실제로 필요한 API 엔드포인트와 사용자 이메일, 모임 번호 등을 적절하게 설정해야 합니다.
    axios.post('/joinGroup/1', { userEmail: 'test@email.com' })
    .then(function (response) {
        alert('모임에 성공적으로 가입하였습니다!');
    })
    .catch(function (error) {
        alert('모임 가입 중 오류가 발생했습니다: ' + error.message);
    });
}