-- testApp 로컬 MySQL 스키마
-- 원본(testApp)은 Oracle 메인 DB + MariaDB(ERP/정보계) 3개로 나뉘어 있었으나,
-- 이 테스트 프로젝트는 로그인 + ERP 조회 + 공통코드관리 + 회원사관리 + 로그/파일 화면만 남기고
-- 로컬 MySQL 하나로 합쳤다. 컬럼 정의는 각 매퍼 XML의 주석(원본 Oracle 컬럼 타입)을 MySQL로 옮긴 것이다.
-- ERP 업무 데이터 조회용 통계/계약 관련 테이블(tbcn_*, su_* 등, erp-mapper.xml의 selectErp* 29종)은
-- 이 1차 스키마에 포함하지 않았다 - 로그인/공통코드/회원관리 화면 확인이 우선이다.

CREATE DATABASE IF NOT EXISTS testApp
    DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_general_ci;

USE testApp;

-- [SY] 회원사(테넌트/회사) 마스터
CREATE TABLE tbsy_member (
    mb_id             VARCHAR(20)   NOT NULL,
    seq               BIGINT        NOT NULL AUTO_INCREMENT,
    mb_nm             VARCHAR(100)  NULL,
    mb_login_id       VARCHAR(20)   NULL,
    copy_mb_id        VARCHAR(20)   NULL,
    zipcd             VARCHAR(7)    NULL,
    addr1             VARCHAR(100)  NULL,
    addr2             VARCHAR(100)  NULL,
    mb_type           VARCHAR(4)    NULL,
    use_yn            VARCHAR(1)    NULL DEFAULT 'Y',
    cont_frymd        VARCHAR(10)   NULL,
    cont_toymd        VARCHAR(10)   NULL,
    corp_reg_num      VARCHAR(12)   NULL,
    tax_reg_num       VARCHAR(20)   NULL,
    corp_email        VARCHAR(100)  NULL,
    represent_nm      VARCHAR(30)   NULL,
    telno             VARCHAR(14)   NULL,
    faxno             VARCHAR(14)   NULL,
    domain_url        VARCHAR(100)  NULL,
    domain_mng        VARCHAR(100)  NULL,
    domain_id         VARCHAR(100)  NULL,
    domain_pw         VARCHAR(100)  NULL,
    ci_url            VARCHAR(200)  NULL,
    logo_url          VARCHAR(200)  NULL,
    manager_nm        VARCHAR(30)   NULL,
    mtelno            VARCHAR(14)   NULL,
    pkg_name          VARCHAR(20)   NULL,
    copy_right        VARCHAR(2000) NULL,
    certlogo_url      VARCHAR(200)  NULL,
    seal_url          VARCHAR(200)  NULL,
    certificate_mb_nm VARCHAR(100)  NULL,
    session_time      INT           NULL DEFAULT 30,
    sort_no           INT           NULL DEFAULT 0,
    memo              VARCHAR(200)  NULL,
    bigo              VARCHAR(100)  NULL,
    secret_key        VARCHAR(32)   NULL,
    iv                VARCHAR(16)   NULL,
    sso_use_yn        VARCHAR(1)    NULL DEFAULT 'N',
    erp_sync_url      VARCHAR(200)  NULL,
    mb_api_id         VARCHAR(20)   NULL,
    mb_api_pwd        VARCHAR(430)  NULL,
    in_emp_cd         VARCHAR(20)   NULL,
    in_dtm            DATETIME      NULL,
    up_emp_cd         VARCHAR(20)   NULL,
    up_dtm            DATETIME      NULL,
    PRIMARY KEY (mb_id),
    UNIQUE KEY uk_tbsy_member_seq (seq)
);

-- [SY] 로그인 계정
CREATE TABLE tbsy_login (
    mb_id            VARCHAR(20)  NOT NULL,
    emp_cd           VARCHAR(20)  NOT NULL,
    login_id         VARCHAR(20)  NOT NULL,
    pwd_intzn_type   VARCHAR(1)   NULL DEFAULT 'N',
    acct_lock_type   VARCHAR(1)   NULL DEFAULT 'N',
    login_autr_type  VARCHAR(1)   NULL DEFAULT 'N',
    user_pwd         VARCHAR(430) NULL,
    trns_user_pwd    VARCHAR(430) NULL,
    pwd_err_nbtm     VARCHAR(10)  NULL DEFAULT '0',
    pwd_chg_dt       VARCHAR(8)   NULL,
    memo             VARCHAR(200) NULL,
    in_emp_cd        VARCHAR(20)  NULL,
    in_dtm           DATETIME     NULL,
    up_emp_cd        VARCHAR(20)  NULL,
    up_dtm           DATETIME     NULL,
    PRIMARY KEY (mb_id, emp_cd),
    UNIQUE KEY uk_tbsy_login_login_id (login_id)
);

-- [SY] 로그인 이력
CREATE TABLE tbsy_login_hist (
    seq        BIGINT      NOT NULL AUTO_INCREMENT,
    mb_id      VARCHAR(20) NULL,
    emp_cd     VARCHAR(20) NULL,
    login_ip   VARCHAR(40) NULL,
    login_dtm  DATETIME    NULL,
    memo       VARCHAR(200) NULL,
    PRIMARY KEY (seq)
);

-- [SY] 로그인 SMS 인증번호 (SMS 발송 모듈 자체는 범위 밖이지만, 인증번호 저장 테이블은 유지)
CREATE TABLE tbsy_login_auth_num (
    mb_id     VARCHAR(20) NOT NULL,
    emp_cd    VARCHAR(20) NOT NULL,
    auth_num  VARCHAR(40) NULL,
    in_dtm    DATETIME    NULL,
    PRIMARY KEY (mb_id, emp_cd)
);

-- [SY] 사용자-권한 매핑
CREATE TABLE tbsy_user_role_map (
    mb_id           VARCHAR(20) NOT NULL,
    emp_cd          VARCHAR(20) NOT NULL,
    concurrent_idx  INT         NOT NULL DEFAULT 1,
    role_id         VARCHAR(50) NULL,
    jikgub          VARCHAR(20) NULL,
    jikchk          VARCHAR(20) NULL,
    scd             VARCHAR(20) NULL,
    PRIMARY KEY (mb_id, emp_cd, concurrent_idx)
);

-- [SY] 권한 계층(자기참조 트리) - Comm.ErpRolesHierarchyTree가 재귀 CTE로 순회
CREATE TABLE tbsy_roles_hierarchy (
    mb_id          VARCHAR(20) NOT NULL,
    prnt_role_id   VARCHAR(50) NOT NULL,
    chld_role_id   VARCHAR(50) NOT NULL,
    in_emp_cd      VARCHAR(20) NULL,
    in_dtm         DATETIME    NULL,
    up_emp_cd      VARCHAR(20) NULL,
    up_dtm         DATETIME    NULL,
    PRIMARY KEY (mb_id, prnt_role_id, chld_role_id)
);

-- [SY] 권한 마스터(erp-mapper.xml selectErpRoleList)
CREATE TABLE tbsy_roles_map (
    mb_id            VARCHAR(20) NOT NULL,
    role_id          VARCHAR(50) NOT NULL,
    role_nm          VARCHAR(50) NULL,
    login_autr_type  VARCHAR(1)  NULL,
    in_emp_cd        VARCHAR(20) NULL,
    in_dtm           DATETIME    NULL,
    up_emp_cd        VARCHAR(20) NULL,
    up_dtm           DATETIME    NULL,
    PRIMARY KEY (mb_id, role_id)
);

-- [IN] 인사 마스터(직원)
CREATE TABLE tbin_empmst (
    mb_id    VARCHAR(20) NOT NULL,
    emp_cd   VARCHAR(20) NOT NULL,
    emp_nm   VARCHAR(50) NULL,
    empsta   VARCHAR(20) NULL DEFAULT '재직',
    jikgub   VARCHAR(20) NULL,
    jikchk   VARCHAR(20) NULL,
    scd      VARCHAR(20) NULL,
    hpno     VARCHAR(20) NULL,
    telno    VARCHAR(20) NULL,
    extno    VARCHAR(20) NULL,
    email    VARCHAR(100) NULL,
    pay_gbn  VARCHAR(20) NULL,
    perno    VARCHAR(20) NULL,
    PRIMARY KEY (mb_id, emp_cd)
);

-- [IN] 조직
CREATE TABLE tbin_scd (
    mb_id  VARCHAR(20) NOT NULL,
    scd    VARCHAR(20) NOT NULL,
    snm    VARCHAR(50) NULL,
    pscd   VARCHAR(20) NULL,
    PRIMARY KEY (mb_id, scd)
);

-- [CM] 공통코드그룹
CREATE TABLE tbcm_common_code_group (
    mb_id            VARCHAR(20)  NOT NULL,
    grp_cmm_cd       VARCHAR(100) NOT NULL,
    grp_cmm_cd_nm    VARCHAR(150) NULL,
    grp_cmm_cd_desc  VARCHAR(300) NULL,
    system_gubun     VARCHAR(10)  NULL,
    sort_no          BIGINT       NULL DEFAULT 0,
    in_emp_cd        VARCHAR(20)  NULL,
    in_dtm           DATETIME     NULL,
    up_emp_cd        VARCHAR(20)  NULL,
    up_dtm           DATETIME     NULL,
    PRIMARY KEY (mb_id, grp_cmm_cd)
);

-- [CM] 공통코드
CREATE TABLE tbcm_common_code (
    mb_id       VARCHAR(20)  NOT NULL,
    grp_cmm_cd  VARCHAR(100) NOT NULL,
    cd_vl       VARCHAR(100) NOT NULL,
    cd_vl_nm    VARCHAR(150) NULL,
    use_yn      VARCHAR(2)   NULL DEFAULT 'Y',
    sort_no     BIGINT       NULL DEFAULT 0,
    in_emp_cd   VARCHAR(20)  NULL,
    in_dtm      DATETIME     NULL,
    up_emp_cd   VARCHAR(20)  NULL,
    up_dtm      DATETIME     NULL,
    PRIMARY KEY (mb_id, grp_cmm_cd, cd_vl)
);

-- [SY] 첨부파일정보
CREATE TABLE tbsy_file_info (
    seq          BIGINT       NOT NULL AUTO_INCREMENT,
    mb_id        VARCHAR(20)  NULL,
    file_no      VARCHAR(40)  NULL,
    attach_gbn   VARCHAR(10)  NULL,
    board_no     VARCHAR(20)  NULL,
    ref_seq      VARCHAR(20)  NULL,
    doc_gbn      VARCHAR(10)  NULL,
    file_path    VARCHAR(300) NULL,
    file_url     VARCHAR(300) NULL,
    file_nm      VARCHAR(300) NULL,
    file_size    BIGINT       NULL,
    file_mime    VARCHAR(100) NULL,
    file_format  VARCHAR(5)   NULL,
    memo         VARCHAR(100) NULL,
    in_emp_cd    VARCHAR(20)  NULL,
    in_dtm       DATETIME     NULL,
    up_emp_cd    VARCHAR(20)  NULL,
    up_dtm       DATETIME     NULL,
    PRIMARY KEY (seq)
);

-- [SY] 액션 로그
CREATE TABLE tbsy_action_log (
    seq                  BIGINT       NOT NULL AUTO_INCREMENT,
    mb_id                VARCHAR(20)  NULL,
    emp_cd               VARCHAR(40)  NULL,
    action_type          VARCHAR(20)  NULL,
    logging_ymd          VARCHAR(10)  NULL,
    action_url           VARCHAR(200) NULL,
    action_query_string  VARCHAR(1000) NULL,
    action_ip            VARCHAR(100) NULL,
    in_dtm               DATETIME     NULL,
    PRIMARY KEY (seq)
);

-- [SY] 에러 로그
CREATE TABLE tbsy_error_log (
    seq                 BIGINT        NOT NULL AUTO_INCREMENT,
    mb_id               VARCHAR(20)   NULL,
    emp_cd              VARCHAR(40)   NULL,
    action_type         VARCHAR(20)   NULL,
    error_url           VARCHAR(200)  NULL,
    error_query_string  VARCHAR(1000) NULL,
    error_status        VARCHAR(40)   NULL,
    error_class         VARCHAR(400)  NULL,
    error_msg           VARCHAR(4000) NULL,
    error_trace         VARCHAR(4000) NULL,
    error_ip            VARCHAR(100)  NULL,
    in_dtm              DATETIME      NULL,
    PRIMARY KEY (seq)
);

-- [SY] 회원가입 인증로그
CREATE TABLE tbsy_join_hist (
    seq        BIGINT       NOT NULL AUTO_INCREMENT,
    send_type  VARCHAR(20)  NULL,
    sender     VARCHAR(50)  NULL,
    reciver    VARCHAR(50)  NULL,
    message    VARCHAR(300) NULL,
    in_dtm     DATETIME     NULL,
    PRIMARY KEY (seq)
);
