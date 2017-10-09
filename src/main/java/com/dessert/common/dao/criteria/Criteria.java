package com.dessert.common.dao.criteria;

import java.util.ArrayList;
import java.util.List;

public class Criteria {

    private String orderByClause;

    private boolean distinct;

    private List<Criterion> criterions;

    public Criteria() {
        criterions = new ArrayList<>();
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public boolean isValid() {
        return criterions.size() > 0;
    }

    public List<Criterion> getCriterions() {
        return criterions;
    }


    public void ORDER_BY_ASC(String column) {
        this.orderByClause = column + " ASC";
    }

    public void ORDER_BY_DESC(String column) {
        this.orderByClause = column + " DESC";
    }


    private void addCriterion(String column, String condition) {
        if (condition == null) {
            throw new RuntimeException("Value for condition cannot be null");
        }
        criterions.add(new Criterion(column, condition));
    }

    private void addCriterion(String column, String condition, Object value) {
        if (value == null) {
            throw new RuntimeException("Value for " + column + " cannot be null");
        }
        criterions.add(new Criterion(column, condition, value));
    }

    private void addCriterion(String column, String condition, Object value1, Object value2) {
        if (value1 == null || value2 == null) {
            throw new RuntimeException("Between values for " + column + " cannot be null");
        }
        criterions.add(new Criterion(column, condition, value1, value2));
    }

    public Criteria WHERE_IsNull(String column) {
        addCriterion(column, "IS NULL");
        return this;
    }

    public Criteria WHERE_IsNotNull(String column) {
        addCriterion(column, "IS NOT NULL");
        return this;
    }

    public Criteria WHERE_EqualTo(String column, Object value) {
        addCriterion(column, "=", value);
        return this;
    }

    public Criteria WHERE_NotEqualTo(String column, Object value) {
        addCriterion(column, "<>", value);
        return this;
    }

    public Criteria WHERE_GreaterThan(String column, Object value) {
        addCriterion(column, ">", value);
        return this;
    }

    public Criteria WHERE_GreaterThanOrEqualTo(String column, Object value) {
        addCriterion(column, ">=", value);
        return this;
    }

    public Criteria WHERE_LessThan(String column, Object value) {
        addCriterion(column, "<", value);
        return this;
    }

    public Criteria WHERE_LessThanOrEqualTo(String column, Object value) {
        addCriterion(column, "<=", value);
        return this;
    }

    public Criteria WHERE_Like(String column, String value) {
        addCriterion(column, "LIKE", "%" + value + "%");
        return this;
    }

    public Criteria WHERE_LikeBefore(String column, String value) {
        addCriterion(column, "LIKE", value + "%");
        return this;
    }

    public Criteria WHERE_LikeAfter(String column, String value) {
        addCriterion(column, "LIKE", "%" + value);
        return this;
    }

    public Criteria WHERE_NotLike(String column, String value) {
        addCriterion(column, "NOT LIKE", "%" + value);
        return this;
    }

    public Criteria WHERE_NotLikeBefore(String column, String value) {
        addCriterion(column, "NOT LIKE", value + "%");
        return this;
    }

    public Criteria WHERE_NotLikeAfter(String column, String value) {
        addCriterion(column, " NOT LIKE", "%" + value);
        return this;
    }

    public Criteria WHERE_In(String column, List<Object> values) {
        addCriterion(column, " IN", values);
        return this;
    }

    public Criteria WHERE_NotIn(String column, List<Object> values) {
        addCriterion(column, " NOT IN", values);
        return this;
    }

    public Criteria WHERE_Between(String column, Object value1, Object value2) {
        addCriterion(column, " BETWEEN", value1, value2);
        return this;
    }

    public Criteria WHERE_NotBetween(String column, Object value1, Object value2) {
        addCriterion(column, " NOT BETWEEN", value1, value2);
        return this;
    }


    public static class Criterion {

        private String column;

        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        public String getColumn() {
            return column;
        }

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        private Criterion(String column, String condition) {
            super();
            this.column = column;
            this.condition = condition;
            this.noValue = true;
        }

        private Criterion(String column, String condition, Object value) {
            super();
            this.column = column;
            this.condition = condition;
            this.value = value;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        private Criterion(String column, String condition, Object value, Object secondValue) {
            super();
            this.column = column;
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.betweenValue = true;
        }

    }
}