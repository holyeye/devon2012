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
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.domain.jaxb.SortAdapter;
import org.springframework.data.domain.jaxb.SpringDataJaxb.SortDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.holyeye.demo.domain.member.Member;
import com.holyeye.demo.domain.member.QMember;
import com.holyeye.demo.repository.MemberRepository;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.types.Expression;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.NumberPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.PathBuilderFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:coreContext.xml")
@Transactional
public class QueryTest {
	
	@Autowired MemberRepository memberRepository;
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
		Pageable pageable = new PageRequest(pageNo, size, sortBy(Direction.DESC, m.age));
		
		OrderSpecifier<Integer> desc = m.age.desc();
		sortBy(desc);
		
		BooleanBuilder where = new BooleanBuilder();
		where.and(m.name.startsWith("김"));
		where.and(m.age.between(20, 40));
//		BooleanExpression exp = m.name.startsWith("김").and(m.age.between(20, 40));
		Page<Member> page = memberRepository.findAll(where, pageable);
		
		List<Member> content = page.getContent();
		for (Member member : content) {
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
