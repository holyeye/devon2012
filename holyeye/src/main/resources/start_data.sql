insert into TEST_ADMENU (id, createdBy, createdDttm, lastModifiedBy, lastModifiedDttm, depth, 
description, displayFlag, displayOrder, menuId, menuName, parentAdMenu_id, targetType, url) 
values (default, NULL, '20121004210757', NULL, '20121004210757', 1, '메뉴관리', 1, 1, 'A01', '메뉴관리', NULL, 'S', '/menu/home');

insert into TEST_ADMENU (id, createdBy, createdDttm, lastModifiedBy, lastModifiedDttm, depth, 
description, displayFlag, displayOrder, menuId, menuName, parentAdMenu_id, targetType, url) 
values (default, NULL, '20121004210919', NULL, '20121004210919', 2, '메뉴 관리', 1, 1, 'A0101', '메뉴 관리', 1, 'S', '/menu/home') 

insert into TEST_ADMENU (id, createdBy, createdDttm, lastModifiedBy, lastModifiedDttm, depth, 
description, displayFlag, displayOrder, menuId, menuName, parentAdMenu_id, targetType, url) 
values (default, NULL, '20121004210957', NULL, '20121004210957', 2, '', 1, 2, 'A0102', '메뉴 통계', 1, 'S', '/menu/home') 

insert into TEST_ADMENU (id, createdBy, createdDttm, lastModifiedBy, lastModifiedDttm, depth, 
description, displayFlag, displayOrder, menuId, menuName, parentAdMenu_id, targetType, url) 
values (default, NULL, '20121004211110', NULL, '20121004211110', 1, '회원관리', 1, 1, 'B01', '회원관리', NULL, 'S', '/member/home') 

insert into TEST_ADMENU (id, createdBy, createdDttm, lastModifiedBy, lastModifiedDttm, depth, 
description, displayFlag, displayOrder, menuId, menuName, parentAdMenu_id, targetType, url) 
values (default, NULL, '20121004211207', NULL, '20121004211207', 2, '회원관리', 1, 1, 'B0101', '회원 관리', 4, 'S', '/member/home') 

insert into TEST_ADMENU (id, createdBy, createdDttm, lastModifiedBy, lastModifiedDttm, depth, 
description, displayFlag, displayOrder, menuId, menuName, parentAdMenu_id, targetType, url) 
values (default, NULL, '20121004211345', NULL, '20121004211345', 2, '', 1, 2, 'B0102', '회원 통계', 4, 'S', '/member/home') 

insert into TEST_ADMENU (id, createdBy, createdDttm, lastModifiedBy, lastModifiedDttm, depth, 
description, displayFlag, displayOrder, menuId, menuName, parentAdMenu_id, targetType, url) 
values (default, NULL, '20121004211625', NULL, '20121004211625', 1, '카드관리', 1, 1, 'C01', '카드관리', NULL, 'S', '/card/home') 

insert into TEST_ADMENU (id, createdBy, createdDttm, lastModifiedBy, lastModifiedDttm, depth, 
description, displayFlag, displayOrder, menuId, menuName, parentAdMenu_id, targetType, url) 
values (default, NULL, '20121004211659', NULL, '20121004211659', 2, '카드 관리', 1, 1, 'C0101', '카드 관리', 7, 'S', '/card/home') 