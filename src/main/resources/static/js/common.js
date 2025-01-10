// 로그아웃 확인 함수
function confirmLogout() {
    const confirmResult = confirm("로그아웃하시겠습니까?");
    if (confirmResult) {
        window.location.href = "http://localhost:8080/";
    }
}




$(document).ready(function () {
    // 기존 DataTables 제거 후 재초기화
    if ($.fn.DataTable.isDataTable('.datatable')) {
        $('.datatable').DataTable().destroy();
    }$(document).ready(function () {
        // 기존 DataTables 제거 후 재초기화
        if ($.fn.DataTable.isDataTable('.datatable')) {
            $('.datatable').DataTable().destroy();
        }

        // DataTables 초기화
        $('.datatable').DataTable({
            dom: "<'row'<'col-sm-12'tr>>" + // 테이블만 상단
                "<'row'<'col-md-6 text-start'f><'col-md-6 text-end'B>>" + // 검색바와 버튼을 하단 배치
                "<'row'<'col-sm-5'i><'col-sm-7'p>>",
            // dom: 'Bfrtip', // 버튼 UI 활성화
            scrollX: true, // 가로 스크롤 활성화
            scrollY: '400px', // 세로 스크롤 높이 설정
            scrollCollapse: true, // 내용이 적으면 스크롤 제거
            autoWidth: true, // 자동 너비 활성화
            paging: false, // 페이징 비활성화
            info: false, // 하단 정보 비활성화
            columnDefs: [
                { targets: 0, width: '5%' }, // 체크박스 열 고정 너비
                { targets: 1, width: '10%' }, // No
                { targets: 1, width: '10%' }, // 사원번호
                { targets: 2, width: '15%' }, // 성명
                { targets: 3, width: '10%' }, // 관리자 여부
                { targets: 4, width: '20%' } // 등록일자
            ],
            fixedColumns: true, // 열 너비 고정
            fixedColumns: true, // 열 너비 고정
            buttons: [
                {
                    extend: 'copy',
                    className: 'btn btn-secondary btn-sm',
                    text: 'Copy'
                },
                {
                    extend: 'excel',
                    className: 'btn btn-secondary btn-sm',
                    text: 'Excel'
                }//,
                // {
                //     extend: 'print',
                //     className: 'btn btn-secondary btn-sm',
                //     text: 'Print'
                // }
            ],
            language: {
                search: "검색:",
                lengthMenu: "페이지당 _MENU_ 개 보기",
                info: "_TOTAL_개의 항목 중 _START_부터 _END_까지 표시",
                infoEmpty: "표시할 데이터가 없습니다.",
                infoFiltered: "(총 _MAX_개의 항목 중 검색됨)",
                zeroRecords: "일치하는 데이터가 없습니다.",
                paginate: {
                    next: "",
                    previous: ""
                }
            }

        });
    });




    // 전체 선택/해제 기능
    $('#selectAll').on('change', function () {
        const isChecked = $(this).is(':checked');
        $('.rowCheckbox').prop('checked', isChecked);
    });

    // 행 체크박스 개별 선택 관리
    $('.datatable').on('change', '.rowCheckbox', function () {
        const allChecked = $('.rowCheckbox').length === $('.rowCheckbox:checked').length;
        $('#selectAll').prop('checked', allChecked);
    });

    // 행 클릭 시 단일 선택 색상 처리
    $('.datatable tbody').on('click', 'tr', function () {
        $('.datatable tbody tr').removeClass('selected'); // 모든 행 색상 초기화
        $(this).addClass('selected'); // 클릭된 행에만 색상 적용
    });
});