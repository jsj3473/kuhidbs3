document.addEventListener("DOMContentLoaded", function () {
    const searchModal = new bootstrap.Modal(document.getElementById('searchModal'));
    const searchInput = document.getElementById('companyIdInput');
    const searchButton = document.getElementById('searchButton');

    // 검색 결과 컨테이너를 화면 중앙 상단에 고정
    const searchResults = document.createElement("div");
    searchResults.className = "search-results list-group position-fixed bg-white border rounded shadow-sm p-2";
    searchResults.style.width = "300px"; // 고정 너비
    searchResults.style.top = "10%"; // 화면의 20% 지점 (살짝 위)
    searchResults.style.left = "50%"; // 가운데 정렬
    searchResults.style.transform = "translateX(-50%)"; // 정확한 중앙 정렬
    searchResults.style.zIndex = "2000"; // 최상위 배치
    searchResults.style.display = "none"; // 초기에는 숨김
    document.body.appendChild(searchResults);

    function openSearchModal(target) {
        searchInput.value = ''; // 입력창 초기화
        searchButton.setAttribute("onclick", `goToPage('${target}')`);
        searchResults.innerHTML = ""; // 검색 결과 초기화
        searchResults.style.display = "none";
        searchModal.show();
        setTimeout(() => searchInput.focus(), 200); // 모달 열리면 자동 포커스
    }

    function goToPage(target, companyId) {
        if (!companyId) {
            alert("회사 고유번호를 입력하세요.");
            return;
        }
        window.location.href = `/${target}/${companyId}`;
    }

    searchInput.addEventListener("input", function () {
        const query = searchInput.value.trim();
        if (!query) {
            searchResults.innerHTML = "";
            searchResults.style.display = "none";
            return;
        }

        fetch(`/api/companies/searchQueryForCompanyInHeader?query=${query}`)
            .then(response => response.json())
            .then(companyIds => {
                searchResults.innerHTML = "";
                searchResults.style.display = "block";
                if (companyIds.length === 0) {
                    searchResults.innerHTML = `<div class='list-group-item text-muted'>검색 결과 없음</div>`;
                } else {
                    companyIds.forEach(companyId => {
                        const resultItem = document.createElement("button");
                        resultItem.className = "list-group-item list-group-item-action";
                        resultItem.textContent = companyId;
                        resultItem.onclick = function () {
                            searchInput.value = companyId;
                            searchResults.innerHTML = "";
                            searchResults.style.display = "none";
                        };
                        searchResults.appendChild(resultItem);
                    });
                }
            })
            .catch(error => console.error("검색 오류:", error));
    });

    // Enter 키 입력 시 검색 실행 및 결과 없음 알림
    searchInput.addEventListener("keypress", function (event) {
        if (event.key === "Enter") {
            if (searchResults.children.length > 0) {
                searchResults.children[0].click();
            } else {
                alert("검색 결과가 없습니다.");
            }
        }
    });

    // 검색 버튼 클릭 시 실행
    searchButton.addEventListener("click", function () {
        if (searchResults.children.length > 0) {
            searchResults.children[0].click();
        } else {
            goToPage(searchButton.getAttribute("onclick").match(/'([^']+)'/)[1], searchInput.value.trim());
        }
    });

    // 전역 함수로 설정 (Thymeleaf에서 호출 가능)
    window.openSearchModal = openSearchModal;
});
