// 로그아웃 확인 함수
function confirmLogout() {
    const confirmResult = confirm("로그아웃하시겠습니까?");
    if (confirmResult) {
        // 서버의 /logout 경로로 이동
        window.location.href = backendUrl + "/api/users/logout";
    }
}



$(document).ready(function () {
    // ✅ 중복 방지
    if ($.fn.DataTable.isDataTable('.datatable')) {
        $('.datatable').DataTable().destroy();
    }

    // ✅ DataTables 초기화
    $('.datatable').DataTable({
        dom: 'Bfrtip',
        scrollX: true,
        scrollY: '700px',
        scrollCollapse: true,
        autoWidth: true,
        paging: true,
        info: false,
        pageLength: 1000,
        columnDefs: [
            { targets: 0, width: '10%' },
            { targets: 1, width: '15%' },
            { targets: 2, width: '10%' },
            { targets: 3, width: '10%' }
        ],
        fixedColumns: true,
        buttons: [
            {
                extend: 'copy',
                className: 'btn btn-secondary btn-sm',
                text: '복사'
            },
            {
                extend: 'excel',
                className: 'btn btn-secondary btn-sm',
                text: '엑셀다운로드'
            }
        ],
        language: {
            search: "검색:",
            lengthMenu: "페이지당 _MENU_ 개 보기",
            info: "_TOTAL_개의 항목 중 _START_부터 _END_까지 표시",
            infoEmpty: "표시할 데이터가 없습니다.",
            infoFiltered: "(총 _MAX_개의 항목 중 검색됨)",
            zeroRecords: "일치하는 데이터가 없습니다.",
            paginate: {
                next: "다음",
                previous: "이전"
            }
        }
    });

    // ✅ 체크박스 전체 선택
    $('#selectAll').on('change', function () {
        const isChecked = $(this).is(':checked');
        $('.rowCheckbox').prop('checked', isChecked);
    });

    // ✅ 개별 체크 시 전체선택 상태 갱신
    $('.datatable').on('change', '.rowCheckbox', function () {
        const allChecked = $('.rowCheckbox').length === $('.rowCheckbox:checked').length;
        $('#selectAll').prop('checked', allChecked);
    });

    // ✅ 행 클릭 시 색상
    $('.datatable tbody').on('click', 'tr', function () {
        $('.datatable tbody tr').removeClass('selected');
        $(this).addClass('selected');
    });
});
