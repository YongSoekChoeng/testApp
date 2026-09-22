-- testApp 테스트용 가라 데이터
-- 로그인 화면(comm/login.jsp)의 회사코드(mb_id) 히든필드 기본값이 'PRD'이므로 mb_id='PRD'로 맞춘다.
-- 로그인 계정: 아이디 admin / 비밀번호 test1234!
--   (user_pwd는 Spring Security StandardPasswordEncoder(빈 secret)로 미리 인코딩한 값)

USE testApp;

INSERT INTO tbsy_member
    (mb_id, mb_nm, mb_login_id, use_yn, cont_frymd, cont_toymd, represent_nm, telno,
     pkg_name, session_time, sort_no, in_emp_cd, in_dtm)
VALUES
    ('PRD', '테스트 회사', 'PRD', 'Y', '20260101', '20991231', '홍길동', '02-1234-5678',
     'PKG_SU_CALC', 30, 10, 'ADMIN001', NOW());

INSERT INTO tbin_scd (mb_id, scd, snm, pscd) VALUES
    ('PRD', 'HQ', '본사', NULL);

INSERT INTO tbin_empmst
    (mb_id, emp_cd, emp_nm, empsta, jikgub, jikchk, scd, hpno, telno, email, pay_gbn, perno)
VALUES
    ('PRD', 'ADMIN001', '관리자', '재직', '관리자', '관리자', 'HQ', '010-1234-5678', '02-1234-5678',
     'admin@testapp-demo.local', 'Y', '9001011234567');

INSERT INTO tbsy_login
    (mb_id, emp_cd, login_id, pwd_intzn_type, acct_lock_type, login_autr_type,
     user_pwd, pwd_err_nbtm, pwd_chg_dt, in_emp_cd, in_dtm)
VALUES
    ('PRD', 'ADMIN001', 'admin', 'N', 'N', 'N',
     'fd8227cdfca9c0c536abd05e84d2bbe23e41adbcd687ec8c3b80b22661004298b7f5614340958434',
     '0', '20260101', 'ADMIN001', NOW());

INSERT INTO tbsy_user_role_map (mb_id, emp_cd, concurrent_idx, role_id, jikgub, jikchk, scd)
VALUES
    ('PRD', 'ADMIN001', 1, 'ROLE_ADMIN', '관리자', '관리자', 'HQ');

INSERT INTO tbsy_roles_map (mb_id, role_id, role_nm, login_autr_type, in_emp_cd, in_dtm)
VALUES
    ('PRD', 'ROLE_ADMIN', '시스템관리자', 'N', 'ADMIN001', NOW());

-- ROLE_ADMIN은 최상위 권한이라 부모가 없다 - tbsy_roles_hierarchy에 행을 넣지 않는다.
-- (Comm.ErpRolesHierarchyTree는 chld_role_id=role_id인 행이 없으면 빈 목록을 돌려줄 뿐이라 로그인에는 문제없다.
--  자기자신을 부모로 넣으면(prnt_role_id=chld_role_id) 재귀 CTE가 무한 순환하니 절대 넣지 않는다.)

-- 공통코드관리 화면에서 바로 확인할 수 있는 샘플 그룹/코드
INSERT INTO tbcm_common_code_group (mb_id, grp_cmm_cd, grp_cmm_cd_nm, grp_cmm_cd_desc, system_gubun, sort_no, in_emp_cd, in_dtm) VALUES
    ('PRD', 'USE_YN', '사용여부', '사용여부 공통코드', 'CM', 10, 'ADMIN001', NOW()),
    ('PRD', 'LOGIN_AUTR_TYPE', '2차인증여부', '로그인 2차인증 사용여부', 'SY', 20, 'ADMIN001', NOW()),
    ('PRD', 'ACTION_TYPE', '액션유형', '액션 로그 유형', 'SY', 30, 'ADMIN001', NOW()),
    ('PRD', 'ATTACH_GBN', '첨부구분', '첨부파일 구분', 'SY', 40, 'ADMIN001', NOW()),
    ('PRD', 'TEST_GROUP', '테스트그룹', '워크플로우 데모용 샘플 그룹', 'DEMO', 50, 'ADMIN001', NOW());

INSERT INTO tbcm_common_code (mb_id, grp_cmm_cd, cd_vl, cd_vl_nm, use_yn, sort_no, in_emp_cd, in_dtm) VALUES
    ('PRD', 'USE_YN', 'Y', '사용', 'Y', 10, 'ADMIN001', NOW()),
    ('PRD', 'USE_YN', 'N', '미사용', 'Y', 20, 'ADMIN001', NOW()),
    ('PRD', 'LOGIN_AUTR_TYPE', 'Y', '사용', 'Y', 10, 'ADMIN001', NOW()),
    ('PRD', 'LOGIN_AUTR_TYPE', 'N', '미사용', 'Y', 20, 'ADMIN001', NOW()),
    ('PRD', 'ACTION_TYPE', 'GO', '페이지이동', 'Y', 10, 'ADMIN001', NOW()),
    ('PRD', 'ACTION_TYPE', 'READ', '조회', 'Y', 20, 'ADMIN001', NOW()),
    ('PRD', 'ATTACH_GBN', 'GENERAL', '일반첨부', 'Y', 10, 'ADMIN001', NOW()),
    ('PRD', 'TEST_GROUP', 'A001', '샘플코드1', 'Y', 10, 'ADMIN001', NOW()),
    ('PRD', 'TEST_GROUP', 'A002', '샘플코드2', 'Y', 20, 'ADMIN001', NOW());
