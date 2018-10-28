function getRightIfEquals(left, right, property) {
    property = property || 'id';
    if (left[property] === right[property]) return right;
    return left;
}

Array.prototype.groupBy = function (prop) {
    return this.reduce(function (groups, item) {
        const val = item[prop];
        groups[val] = groups[val] || [];
        groups[val].push(item);
        return groups;
    }, {})
};

function executeIfEmpty(element, fun) {
    if (isEmpty(element)) {
        fun();
    }
}

function isEmpty(obj) {
    if (obj === undefined || obj === null) return true;
    if (Array.isArray(obj)) return obj.length === 0;
    if (typeof obj === "string") return obj.replace(' ', '') === '';
    if (typeof obj === "object") return Object.keys(obj).length === 0 || Object.values(obj).every(value => isEmpty(value));
    return false;
}

function isNotEmpty(obj) {
    return !isEmpty(obj);
}