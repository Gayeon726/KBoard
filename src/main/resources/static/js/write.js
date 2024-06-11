$(function(){

    // [추가] 버튼 누르면 첨부 파일 추가 가능
    // [추가] 버튼을 클릭할 때마다 <div>가 추가됨
    // [삭제] 버튼을 누르면 삭제
    // <input>의 name 값이 중복이 안되도록 생성시 i++ ( upfiles(0) upfiles(1) ... )
    let i = 0;
    $("#btnAdd").click(function(){
        $("#files").append(`
            <div class="input-group mb-2">
               <input class="form-control col-xs-3" type="file" name="upfile${i}"/> 
               <button type="button" class="btn btn-outline-danger" onclick="$(this).parent().remove()">삭제</button>
            </div>
        `);
        i++;
    });

    // Summernote 추가
    $("#content").summernote({
        height: 300,
    });
});