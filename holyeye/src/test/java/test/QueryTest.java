package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.holyeye.demo.domain.MemberDTO;
import com.holyeye.demo.domain.QMemberDTO;
import com.holyeye.demo.domain.card.Card;
import com.holyeye.demo.domain.card.QCard;
import com.holyeye.demo.domain.member.Member;
import com.holyeye.demo.domain.member.QMember;
import com.holyeye.demo.domain.membercard.QMemberCard;
import com.holyeye.demo.repository.CardRepository;
import com.holyeye.demo.repository.MemberRepository;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPASubQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.expr.NumberExpression;
import com.mysema.query.types.path.NumberPath;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:coreContext.xml")
@Transactional
public class QueryTest {
	
	@Autowired MemberRepository memberRepository;
	@Autowired CardRepository cardRepository;
	@PersistenceContext
	private EntityManager entityManager;
	
	@Before
	public void init() {
		Member newMember = new Member();
		newMember.setName("김영한");
		newMember.setAge(20);
		entityManager.persist(newMember);
		entityManager.flush();
		entityManager.clear();		
	}
	

	@Test
	public void interfaceWithPagingQuery() {
		
		int pageNo = 0;	int size = 3;
		Pageable pageable = new PageRequest(pageNo, size, Sort.Direction.DESC, "age");
		Page<Member> result = memberRepository.findByNameStartingWithAndAgeBetweenOrderByAgeDesc("김", 20, 40, pageable);
		
		List<Member> list = result.getContent();
		for (Member member : list) {
			System.out.println(member.getName());
		}
	}
	
	@Test
	public void querydslQuery() {
		
		QMember m = QMember.member;
		
		int pageNo = 0;	int size = 3;
		Pageable pageable = new PageRequest(pageNo, size, Direction.DESC, "age");	//SpringData 다음버전에서 type-safe도 지원
		BooleanExpression predicate = m.name.startsWith("김").and(m.age.between(20, 40));
		Page<Member> page = memberRepository.findAll(predicate, pageable);
		
		List<Member> content = page.getContent();
		for (Member member : content) {
			System.out.println(member.getName());
		}
		
	}
	
	@Test
	public void querydslWithBuilderQuery() {
		
		QMember m = QMember.member;
		
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(m.name.startsWith("김"));
		builder.and(m.age.between(20, 40));
		Iterable<Member> results = memberRepository.findAll(builder);
		
		for (Member member : results) {
			System.out.println(member.getName());
		}
	}
	
	@Test
	public void querydslWithBuilderDynamicQuery() {

		String str = "김";
		int min=20,max=40;
		
		QMember m = QMember.member;
		
		BooleanBuilder builder = new BooleanBuilder();
		if (StringUtils.hasText(str))
			builder.and(m.name.startsWith(str));
		
		if (min != 0 && max != 0)
			builder.and(m.age.between(min, max));
		
		Iterable<Member> results = memberRepository.findAll(builder);
		
		for (Member member : results) {
			System.out.println(member.getName());
		}
	}
	
	@Test
	public void querydslWithJoin() {
		
		String str = "김";
		int min=20,max=40;
		
		QMember m = QMember.member;
		QMemberCard mc = QMemberCard.memberCard;
		
		JPAQuery query = new JPAQuery(entityManager);
		
		List<Member> list = 
				query.from(m).innerJoin(m.memberCards, mc)
				.where(m.age.between(20, 40))
				.list(m);
		
		for (Member member : list) {
			System.out.println(member.getName());
		}
	}
	
	@Test
	public void querydslWithSubquery() {
		
		String str = "김";
		int min=20,max=40;
		
		QMember m = QMember.member;
		QMember msub = new QMember("msub");
		
		JPAQuery query = new JPAQuery(entityManager);
		
		List<Member> list = 
				query.from(m).where(
						m.in(new JPASubQuery().from(msub).where(msub.age.between(20, 40)).list(msub)))
				.list(m);
		//select member0_.id as id4_, member0_.age as age4_, member0_.name as name4_ from Member member0_ 
//		where member0_.id in (select member1_.id from Member member1_ where member1_.age between 20 
//				and 40) 
		for (Member member : list) {
			System.out.println(member.getName());
		}
	}
	
	@Test
	public void querydslWithJoinDTO() {
		
		String str = "김";
		int min=20,max=40;
		
		QMember m = QMember.member;
		QMemberCard mc = QMemberCard.memberCard;
		
		JPAQuery query = new JPAQuery(entityManager);
		
		List<MemberDTO> list = 
				query.from(m).innerJoin(m.memberCards, mc)
				.where(m.age.between(20, 40))
				.groupBy(m.id, m.name, m.age)
//				.list(ConstructorExpression.create(MemberDTO.class, m.id, m.name, m.age));	//순수하게
				.list(new QMemberDTO(m.id, m.name, m.age, m.count()));	//@QueryProjection으로 생성된 QType으로 
		
		for (MemberDTO member : list) {
			System.out.println("print=" + member);
		}
	}
	
	@Test
	public void querydslDelegateMethodQuery() {

		String firstname = "김";
		
		QMember m = QMember.member;
//		Iterable<Member> results = memberRepository.findAll(m.isAdult().and(m.hasFirstname(firstname)));
//		
//		for (Member member : results) {
//			System.out.println(member.getName());
//		}
		
	}
	
	@Test
	public void querydslQuery2() {
		
		QCard c = QCard.card;
		
//		NumberPath<Long> id = c._super.as(NumberPath.class);
		
		BooleanExpression predicate = c.name.like("%%").and(c.id.eq(1L));
		
		Iterable<Card> list = cardRepository.findAll(predicate);
		
		for (Card member : list) {
			System.out.println(member.getName());
		}
		
	}	
	

	private Sort sortBy(OrderSpecifier order) {
//		Expression target = order.getTarget();
//	    return new Sort(order.isAscending() ? Direction.ASC : Direction.DESC,
//	    		order.getTarget().path.getMetadata().getExpression().toString());
		return null;
	}

	
	private Sort sortBy(Direction direction, com.mysema.query.types.Path<?> path) {
	    return new Sort(direction, path.getMetadata().getExpression().toString());
	}
	
	public static void main(String[] args) {
		QMember m = QMember.member;
		
		m.age.desc().getTarget();
		System.out.println(m.age.desc().getTarget().toString());
		
		NumberPath<Integer> target = (NumberPath<Integer>) m.age.desc().getTarget();
		
		System.out.println(m.age.getMetadata().getExpression());
	}
	
	
	@Test
	public void speficicationsQuery() {

		Specification<Member> firstNameLike = MemberSpecs.isFirstName("김");
		Specification<Member> ageBetween = MemberSpecs.ageBetween(20, 40);
		
		Specifications<Member> specs = Specifications.where(firstNameLike).and(ageBetween);
	
		int pageNo = 0;	int size = 3;
		Pageable pageable = new PageRequest(pageNo, size, Sort.Direction.DESC, "age");
		
		Page<Member> pageResult = memberRepository.findAll(specs, pageable);
		List<Member> list = pageResult.getContent();
		
		for (Member member : list) {
			System.out.println(member.getName());
		}
		
	}
	
	public static class MemberSpecs {

		public static Specification<Member> isFirstName(final String firstName){
			return new Specification<Member>() {
				@Override
				public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					Path<String> namePath = root.get("name");
					return cb.like(namePath, firstName+"%");
				}
			};
		}
		
		public static Specification<Member> ageBetween(final int min, final int max){
			return new Specification<Member>() {
				@Override
				public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query,
						CriteriaBuilder cb) {
					Path<Integer> age = root.get("age");
					return cb.between(age, min, max);
				}
			};
		}		
	}
}
