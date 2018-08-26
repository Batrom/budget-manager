function getRightIfEquals(left, right, property) {
    property = property || 'id';
    if (left[property] === right[property]) return right;
    return left
}